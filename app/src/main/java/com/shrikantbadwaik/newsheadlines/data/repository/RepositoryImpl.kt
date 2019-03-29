package com.shrikantbadwaik.newsheadlines.data.repository

import com.shrikantbadwaik.newsheadlines.BuildConfig
import com.shrikantbadwaik.newsheadlines.data.local.PreferenceHelper
import com.shrikantbadwaik.newsheadlines.data.remote.NewsApi
import com.shrikantbadwaik.newsheadlines.domain.model.News
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val prefs: PreferenceHelper, private val api: NewsApi
) : Repository {
    override fun getTopHeadlines(country: String, category: String, query: String?): Observable<News> {
        return api.topHeadlines(BuildConfig.API_KEY, country, category, query, 30)
    }
}