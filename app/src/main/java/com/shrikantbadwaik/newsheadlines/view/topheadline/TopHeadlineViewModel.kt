package com.shrikantbadwaik.newsheadlines.view.topheadline

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.data.remote.CallbackObserverWrapper
import com.shrikantbadwaik.newsheadlines.data.repository.Repository
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import com.shrikantbadwaik.newsheadlines.domain.model.News
import com.shrikantbadwaik.newsheadlines.domain.util.AppUtil
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(
    private val ctx: Application, private val repository: Repository,
    private val appUtil: AppUtil
) : AndroidViewModel(ctx) {
    private var disposable: Disposable? = null
    private val dialogStateLiveData: MutableLiveData<String> = MutableLiveData()
    private var keyboardStateLiveData: MutableLiveData<String> = MutableLiveData()
    private val apiResultLiveData: MutableLiveData<String> = MutableLiveData()
    private var apiErrorsLiveData: MutableLiveData<String> = MutableLiveData()
    private val newsArticleLiveData: MutableLiveData<ArrayList<Article>> = MutableLiveData()
    private var newsArticleObservable: ObservableArrayList<Article> = ObservableArrayList()
    private val searchLoading: ObservableBoolean = ObservableBoolean(false)
    private val loading: ObservableBoolean = ObservableBoolean(false)

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun getDialogStateLiveData() = dialogStateLiveData

    fun getKeyboardStateLiveData() = keyboardStateLiveData

    fun getApiResultLiveData() = apiResultLiveData

    fun getApiErrorLiveData() = apiErrorsLiveData

    fun getNewsArticleLiveData() = newsArticleLiveData

    fun setNewsArticleObservable(articles: ArrayList<Article>?) {
        articles?.let {
            if (it.isNotEmpty()) {
                newsArticleObservable.clear()
                newsArticleObservable.addAll(it)
            }
        }
    }

    fun getNewsArticleObservable() = newsArticleObservable

    fun setSearchLoading(state: Boolean) {
        searchLoading.set(state)
    }

    fun isSearchLoading(): ObservableBoolean = searchLoading

    fun setLoading(state: Boolean) {
        loading.set(state)
    }

    fun isLoading(): ObservableBoolean = loading

    fun getTopHeadlines(search: Boolean, query: String?) {
        if (search) {
            if (query.isNullOrEmpty()) {
                dialogStateLiveData.value = Constants.DialogState.QUERY_EMPTY_ERROR_DIALOG.name
                keyboardStateLiveData.value = Constants.KeyboardState.SHOW.name
            } else {
                keyboardStateLiveData.value = Constants.KeyboardState.HIDE.name
                news(search, query)
            }
        } else {
            keyboardStateLiveData.value = Constants.KeyboardState.HIDE.name
            news(search, null)
        }
    }

    private fun news(search: Boolean, query: String?) {
        if (AppUtil.isDeviceOnline(ctx)) {
            if (search) {
                setSearchLoading(true)
                setLoading(false)
            } else {
                setSearchLoading(false)
                setLoading(true)
            }
            disposable = repository.getTopHeadlines("in", query)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackObserverWrapper<News>() {
                    override fun onSuccess(result: News) {
                        setSearchLoading(false)
                        setLoading(false)
                        if (!result.articles.isNullOrEmpty()) {
                            apiResultLiveData.value = Constants.ApiResult.ON_SUCCESS.name
                            newsArticleLiveData.value = result.articles
                        } else {
                            apiResultLiveData.value = Constants.ApiResult.ON_FAILURE.name
                            if (search) apiErrorsLiveData.value = appUtil.getString(R.string.txt_search_failed)
                        }
                    }

                    override fun onFailure(error: String) {
                        setSearchLoading(false)
                        setLoading(false)
                        apiResultLiveData.value = Constants.ApiResult.ON_FAILURE.name
                        apiErrorsLiveData.value = error
                    }

                    override fun onCompleted() {
                        setSearchLoading(false)
                        setLoading(false)
                        apiResultLiveData.value = Constants.ApiResult.ON_COMPLETED.name
                    }
                })
        } else dialogStateLiveData.value = Constants.DialogState.DEVICE_OFFLINE_DIALOG.name
    }
}