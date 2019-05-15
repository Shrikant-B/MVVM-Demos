package com.shrikantbadwaik.newsheadlines.data.remote

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.shrikantbadwaik.newsheadlines.domain.model.ApiErrors
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class CallbackSingleWrapper<R>: DisposableSingleObserver<R>() {
    protected abstract fun onApiSuccess(result: R)
    protected abstract fun onApiFailure(error: String)

    override fun onSuccess(result: R) {
        onApiSuccess(result)
    }

    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                val responseBody = e.response().errorBody()
                onApiFailure(getErrorMessage(responseBody))
            }
            is SocketTimeoutException -> onApiFailure("Network Timeout!")
            is IOException -> onApiFailure("Failed to connect to server!")
            else -> onApiFailure(e.localizedMessage.toString())
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody?.string())
            val objectMapper = ObjectMapper()
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            val apiErrors = objectMapper.readValue(jsonObject.toString(), ApiErrors::class.java)
            apiErrors.message.toString()
        } catch (e: Exception) {
            e.message.toString()
        }
    }
}