package com.shrikantbadwaik.searchtweets.data.repository

import com.shrikantbadwaik.searchtweets.data.local.PreferenceHelper
import com.shrikantbadwaik.searchtweets.data.remote.TwitterApi
import com.shrikantbadwaik.searchtweets.domain.model.Tweets
import com.shrikantbadwaik.searchtweets.domain.model.TwitterAccessToken
import com.shrikantbadwaik.searchtweets.domain.util.AppUtil
import com.shrikantbadwaik.searchtweets.domain.util.Constants
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val prefs: PreferenceHelper, private val api: TwitterApi
) : Repository {
    override fun setAccessToken(accessToken: String?) {
        prefs.putString(prefs.accessToken, accessToken)
    }

    override fun getAccessToken() = prefs.getString(prefs.accessToken, null)

    override fun generateAccessToken(): Observable<TwitterAccessToken> {
        return api.generateAccessToken(
            "Basic ${AppUtil.base64String(Constants.BEARER_TOKEN_CREDENTIALS)}", Constants.GRANT_TYPE
        )
    }

    override fun mostRecentTweets(query: String): Observable<Tweets> {
        return api.mostRecentTweets("Bearer ${getAccessToken()}", query, Constants.RESULT_TYPE, 50)
    }
}