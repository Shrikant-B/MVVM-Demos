package com.shrikantbadwaik.newsheadlines.data.repository

import com.shrikantbadwaik.newsheadlines.domain.model.News
import io.reactivex.Observable

interface Repository {
    fun getTopHeadlines(countryName: String, query: String?): Observable<News>
}