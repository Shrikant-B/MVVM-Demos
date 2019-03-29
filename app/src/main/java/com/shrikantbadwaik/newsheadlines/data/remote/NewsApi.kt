package com.shrikantbadwaik.newsheadlines.data.remote

import com.shrikantbadwaik.newsheadlines.domain.model.News
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    fun topHeadlines(
        @Header("x-api-key") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("pageSize") pageSize: Int
    ): Observable<News>
}