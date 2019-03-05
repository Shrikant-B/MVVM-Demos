package com.shrikantbadwaik.searchtweets.data.remote

import com.shrikantbadwaik.searchtweets.domain.util.Constants
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
            else -> onFailure(e.localizedMessage.toString())
        }
    }

    override fun onComplete() {
        onCompleted()
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody?.string())
            val jsonArray = jsonObject.getJSONArray(Constants.API_ERROR_RESPONSE_KEY)
            jsonArray.getString(0)
        } catch (e: Exception) {
            e.message.toString()
        }
    }
}