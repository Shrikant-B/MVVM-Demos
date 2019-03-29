package com.shrikantbadwaik.newsheadlines.domain.util

object Constants {
    const val DURATION_4000: Long = 4000L
    const val ARG_ARTICLE = "article"
    val COUNTRIES = arrayOf("in", "us", "gb", "fr", "de", "au", "ru")
    val CATEGORIES = arrayOf("general", "business", "entertainment", "health", "science", "sports", "technology")

    enum class ActivityCallingState {
        CALL_TOP_HEADLINE_ACTIVITY
    }

    enum class DialogState {
        QUERY_EMPTY_ERROR_DIALOG, API_ERROR_DIALOG, DEVICE_OFFLINE_DIALOG,
    }

    enum class ApiResult {
        ON_SUCCESS, ON_FAILURE, ON_COMPLETED
    }

    enum class KeyboardState {
        SHOW, HIDE
    }

    enum class ClickEventState {
        OPEN_WEB_BROWSER, SELECT_COUNTRY_DIALOG, SELECT_CATEGORY_DIALOG
    }
}