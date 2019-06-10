package com.shrikantbadwaik.newsheadlines.view.topheadline

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.data.remote.CallbackObserverWrapper
import com.shrikantbadwaik.newsheadlines.data.remote.CallbackSingleWrapper
import com.shrikantbadwaik.newsheadlines.data.repository.Repository
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import com.shrikantbadwaik.newsheadlines.domain.util.AppUtil
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class TopHeadlineViewModel : ViewModel(), KoinComponent {
    private val repository: Repository by inject()
    private val appUtil: AppUtil by inject()

    private var disposable: Disposable? = null
    private val dialogStateLiveData: MutableLiveData<String> = MutableLiveData()
    private val keyboardStateLiveData: MutableLiveData<String> = MutableLiveData()
    private val apiResultLiveData: MutableLiveData<String> = MutableLiveData()
    private val apiErrorsLiveData: MutableLiveData<String> = MutableLiveData()
    private val newsArticleLiveData: MutableLiveData<ArrayList<Article>> = MutableLiveData()
    private val newsArticleObservable: ObservableArrayList<Article> = ObservableArrayList()
    private val clickEventLiveData: MutableLiveData<String> = MutableLiveData()
    private val searchLoading: ObservableBoolean = ObservableBoolean(false)
    private val loading: ObservableBoolean = ObservableBoolean(false)
    private var country: String = Constants.COUNTRIES[0]
    private var category: String = Constants.CATEGORIES[0]

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

    fun getClickEventLiveData() = clickEventLiveData

    fun setSearchLoading(state: Boolean) {
        searchLoading.set(state)
    }

    fun isSearchLoading(): ObservableBoolean = searchLoading

    fun setLoading(state: Boolean) {
        loading.set(state)
    }

    fun isLoading(): ObservableBoolean = loading

    fun onSelectCountryClicked() {
        clickEventLiveData.value = Constants.ClickEventState.SELECT_COUNTRY_DIALOG.name
    }

    fun onSelectCategoryClicked() {
        clickEventLiveData.value = Constants.ClickEventState.SELECT_CATEGORY_DIALOG.name
    }

    fun selectCountry(ctx: Context, categories: Array<String>, country: String, category: String) {
        if (!country.equals(appUtil.getString(R.string.txt_select_country))) {
            this.country = country
            if (categories.contains(category)) {
                this.category = Constants.CATEGORIES[categories.indexOf(category)]
            } else this.category = Constants.CATEGORIES[0]
        } else this.country = Constants.COUNTRIES[0]

        getTopHeadlines(ctx, false, null)
    }

    fun selectCategory(ctx: Context, countries: Array<String>, country: String, category: String) {
        if (!category.equals(appUtil.getString(R.string.txt_select_category))) {
            this.category = category
            if (countries.contains(country)) {
                this.country = Constants.COUNTRIES[countries.indexOf(country)]
            } else this.country = Constants.COUNTRIES[0]
        } else this.category = Constants.CATEGORIES[0]

        getTopHeadlines(ctx, false, null)
    }

    fun getTopHeadlines(ctx: Context, search: Boolean, query: String?) {
        if (search) {
            if (query.isNullOrEmpty()) {
                dialogStateLiveData.value = Constants.DialogState.QUERY_EMPTY_ERROR_DIALOG.name
                keyboardStateLiveData.value = Constants.KeyboardState.SHOW.name
            } else {
                keyboardStateLiveData.value = Constants.KeyboardState.HIDE.name
                news(ctx, search, query)
            }
        } else {
            keyboardStateLiveData.value = Constants.KeyboardState.HIDE.name
            news(ctx, search, null)
        }
    }

    private fun news(ctx: Context, search: Boolean, query: String?) {
        if (AppUtil.isDeviceOnline(ctx)) {
            if (search) {
                setSearchLoading(true)
                setLoading(false)
            } else {
                setSearchLoading(false)
                setLoading(true)
            }
            disposable = repository.getTopHeadlines(country, category, query)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackSingleWrapper<List<Article>>() {
                    override fun onApiSuccess(result: List<Article>) {
                        setSearchLoading(false)
                        setLoading(false)
                        if (!result.isNullOrEmpty()) {
                            apiResultLiveData.value = Constants.ApiResult.ON_SUCCESS.name
                            newsArticleLiveData.value = result as ArrayList<Article>
                        } else {
                            apiResultLiveData.value = Constants.ApiResult.ON_FAILURE.name
                            if (search) apiErrorsLiveData.value = appUtil.getString(R.string.txt_search_failed)
                        }
                    }

                    override fun onApiFailure(error: String) {
                        setSearchLoading(false)
                        setLoading(false)
                        apiResultLiveData.value = Constants.ApiResult.ON_FAILURE.name
                        apiErrorsLiveData.value = error
                    }
                })
        } else {
            if (search) {
                setSearchLoading(true)
                setLoading(false)
            } else {
                setSearchLoading(false)
                setLoading(true)
            }
            disposable = repository.getOfflineArticles(search, country, category, query)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackObserverWrapper<List<Article>>() {
                    override fun onApiSuccess(result: List<Article>) {
                        setSearchLoading(false)
                        setLoading(false)
                        if (!result.isNullOrEmpty()) {
                            apiResultLiveData.value = Constants.ApiResult.ON_SUCCESS.name
                            newsArticleLiveData.value = result as ArrayList<Article>
                        } else {
                            apiResultLiveData.value = Constants.ApiResult.ON_FAILURE.name
                            if (search) apiErrorsLiveData.value = appUtil.getString(R.string.txt_search_failed)
                            else dialogStateLiveData.value = Constants.DialogState.DEVICE_OFFLINE_DIALOG.name
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
        }
    }
}