package com.shrikantbadwaik.searchtweets.view.searchtweets

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import com.shrikantbadwaik.searchtweets.data.remote.CallbackObserverWrapper
import com.shrikantbadwaik.searchtweets.data.repository.Repository
import com.shrikantbadwaik.searchtweets.domain.model.Tweet
import com.shrikantbadwaik.searchtweets.domain.model.Tweets
import com.shrikantbadwaik.searchtweets.domain.model.TwitterAccessToken
import com.shrikantbadwaik.searchtweets.domain.util.AppUtil
import com.shrikantbadwaik.searchtweets.domain.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchTweetsViewModel @Inject constructor(
    private val ctx: Application, private val repository: Repository
) : AndroidViewModel(ctx) {
    private var disposable: Disposable? = null
    private val validationLiveData: MutableLiveData<String> = MutableLiveData()
    private val dialogStateLiveData: MutableLiveData<String> = MutableLiveData()
    private val apiResultLiveData: MutableLiveData<String> = MutableLiveData()
    private var apiErrorsLiveData: MutableLiveData<String> = MutableLiveData()
    private val tweetsLiveData: MutableLiveData<ArrayList<Tweet>> = MutableLiveData()
    private val tweetsObservable: ObservableArrayList<Tweet> = ObservableArrayList()
    private val loading: ObservableBoolean = ObservableBoolean(false)

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun getValidationLiveData() = validationLiveData

    fun getDialogStateLiveData() = dialogStateLiveData

    fun getApiResultLiveData() = apiResultLiveData

    fun getApiErrorLiveData() = apiErrorsLiveData

    fun getTweetsLiveData() = tweetsLiveData

    fun setTweetsObservable(tweets: ArrayList<Tweet>?) {
        tweets?.let {
            if (it.isNotEmpty()) {
                tweetsObservable.clear()
                tweetsObservable.addAll(it)
            }
        }
    }

    fun getTweetsObservable() = tweetsObservable

    fun setLoading(state: Boolean) {
        loading.set(state)
    }

    fun isLoading(): ObservableBoolean = loading

    fun getMostRecentTweets(query: String?) {
        if (query.isNullOrEmpty()) {
            validationLiveData.value = Constants.Validations.QUERY_EMPTY.name
        } else {
            validationLiveData.value = Constants.Validations.EVERYTHING_OK.name
            if (repository.getAccessToken().isNullOrEmpty()) {
                generateAccessToken(query)
            } else searchMostRecentTweets(query)
        }
    }

    private fun generateAccessToken(query: String) {
        if (AppUtil.isDeviceOnline(ctx)) {
            setLoading(true)
            disposable = repository.generateAccessToken()
                .flatMap(object : Function<TwitterAccessToken, Observable<Tweets>> {
                    override fun apply(token: TwitterAccessToken): Observable<Tweets> {
                        repository.setAccessToken(token.accessToken)
                        return repository.mostRecentTweets(query)
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackObserverWrapper<Tweets>() {
                    override fun onSuccess(result: Tweets) {
                        setLoading(false)
                        apiResultLiveData.value = Constants.ApiResult.ON_SUCCESS.name
                        tweetsLiveData.value = result.tweets
                    }

                    override fun onFailure(error: String) {
                        setLoading(false)
                        apiResultLiveData.value = Constants.ApiResult.ON_FAILURE.name
                        apiErrorsLiveData.value = error
                    }

                    override fun onCompleted() {
                        setLoading(false)
                        apiResultLiveData.value = Constants.ApiResult.ON_COMPLETED.name
                    }
                })
        } else dialogStateLiveData.value = Constants.DialogState.DEVICE_OFFLINE_DIALOG.name
    }

    private fun searchMostRecentTweets(query: String) {
        setLoading(true)
        disposable = repository.mostRecentTweets(query)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CallbackObserverWrapper<Tweets>() {
                override fun onSuccess(result: Tweets) {
                    setLoading(false)
                    apiResultLiveData.value = Constants.ApiResult.ON_SUCCESS.name
                    tweetsLiveData.value = result.tweets
                }

                override fun onFailure(error: String) {
                    setLoading(false)
                    apiResultLiveData.value = Constants.ApiResult.ON_FAILURE.name
                }

                override fun onCompleted() {
                    setLoading(false)
                    apiResultLiveData.value = Constants.ApiResult.ON_COMPLETED.name
                }
            })
    }
}