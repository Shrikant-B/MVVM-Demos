package com.shrikantbadwaik.searchtweets.data.repository

import com.shrikantbadwaik.searchtweets.domain.model.Tweets
import com.shrikantbadwaik.searchtweets.domain.model.TwitterAccessToken
import io.reactivex.Observable

interface Repository {
    fun setAccessToken(accessToken: String?)
    fun getAccessToken(): String?
    fun generateAccessToken(): Observable<TwitterAccessToken>
    fun mostRecentTweets(query: String): Observable<Tweets>
}