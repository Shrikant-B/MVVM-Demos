package com.shrikantbadwaik.newsheadlines.data.repository

import com.shrikantbadwaik.newsheadlines.domain.model.News
import io.reactivex.Observable

interface Repository {
    fun getTopHeadlines(country: String, category: String, query: String?): Observable<News>
}