package com.shrikantbadwaik.newsheadlines.data.remote

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.shrikantbadwaik.newsheadlines.domain.model.ApiErrors
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class CallbackObserverWrapper<R> : DisposableObserver<R>() {
    protected abstract fun onSuccess(result: R)
    protected abstract fun onFailure(error: String)
    protected abstract fun onCompleted()

    override fun onNext(result: R) {
        onSuccess(result)
    }

    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                val responseBody = e.response().errorBody()
                onFailure(getErrorMessage(responseBody))
            }
            is SocketTimeoutException -> onFailure("Network Timeout!")
            is IOException -> onFailure("Failed to connect to server!")
            else -> onFailure(e.localizedMessage.toString())
        }
    }

    override fun onComplete() {
        onCompleted()
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody?.string())
            val objectMapper = ObjectMapper()
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            val apiErrors = objectMapper.readValue(jsonObject.toString(), ApiErrors::class.java)
            return apiErrors.message.toString()
        } catch (e: Exception) {
            e.message.toString()
        }
    }
}