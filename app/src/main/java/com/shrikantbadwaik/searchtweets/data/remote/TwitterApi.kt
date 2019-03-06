package com.shrikantbadwaik.searchtweets.data.remote

import com.shrikantbadwaik.searchtweets.domain.model.Tweets
import com.shrikantbadwaik.searchtweets.domain.model.TwitterAccessToken
import io.reactivex.Observable
import retrofit2.http.*

interface TwitterApi {
    @FormUrlEncoded
    @POST("/oauth2/token")
    fun generateAccessToken(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String
    ): Observable<TwitterAccessToken>

    @GET("/1.1/search/tweets.json")
    fun mostRecentTweets(
        @Header("Authorization") authorization: String,
        @Query("q") query: String,
        @Query("result_type") resultType: String,
        @Query("count") count: Int
    ): Observable<Tweets>
}