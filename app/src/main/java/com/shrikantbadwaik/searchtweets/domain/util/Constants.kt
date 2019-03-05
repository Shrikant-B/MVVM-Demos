package com.shrikantbadwaik.searchtweets.domain.util

object Constants {
    const val DURATION_4000: Long = 4000L
    const val API_ERROR_RESPONSE_KEY = "ERROR_KEY_HERE"

    enum class ActivityCallingState {
        CALL_SEARCH_TWEET_ACTIVITY
    }

    enum class DialogState {
        API_ERROR_DIALOG, DEVICE_OFFLINE_DIALOG
    }

    enum class DeviceState {
        ONLINE, OFFLINE
    }

    enum class Request {
        ON_SUCCESS, ON_FAILURE, ON_COMPLETED
    }
}