package com.shrikantbadwaik.searchtweets.domain.util

import com.shrikantbadwaik.searchtweets.BuildConfig

object Constants {
    const val DURATION_4000: Long = 4000L
    const val API_ERROR_RESPONSE_KEY = "errors"
    const val BEARER_TOKEN_CREDENTIALS = "${BuildConfig.API_KEY}:${BuildConfig.SECRET_KEY}"
    const val GRANT_TYPE = "client_credentials"
    const val RESULT_TYPE = "recent"

    enum class ActivityCallingState {
        CALL_SEARCH_TWEET_ACTIVITY
    }

    enum class DialogState {
        QUERY_EMPTY_ERROR_DIALOG, API_ERROR_DIALOG, DEVICE_OFFLINE_DIALOG
    }

    enum class ApiResult {
        ON_SUCCESS, ON_FAILURE, ON_COMPLETED
    }
}