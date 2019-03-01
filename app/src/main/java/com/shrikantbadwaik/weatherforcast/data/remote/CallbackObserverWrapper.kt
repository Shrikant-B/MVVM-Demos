package com.shrikantbadwaik.weatherforcast.data.remote

import com.shrikantbadwaik.weatherforcast.domain.Constants
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
            is SocketTimeoutException -> onFailure("Network Timeout")
            is IOException -> onFailure("Failed to connect to server")
            else -> onFailure(e.localizedMessage)
        }
    }

    override fun onComplete() {
        onCompleted()
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        var error = "Unknown error occurred"
        return try {
            error = responseBody!!.string()
            val jsonObject = JSONObject(error)
            jsonObject.getString(Constants.API_ERROR_RESPONSE_KEY)
        } catch (e: Exception) {
            error
        }
    }
}