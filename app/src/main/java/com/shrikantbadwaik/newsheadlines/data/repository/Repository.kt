package com.shrikantbadwaik.newsheadlines.data.repository

import com.shrikantbadwaik.newsheadlines.domain.model.Article
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {
    fun getTopHeadlines(country: String, category: String, query: String?): Single<List<Article>>
    fun getOfflineArticles(search: Boolean, country: String, category: String, query: String?): Maybe<List<Article>>
}