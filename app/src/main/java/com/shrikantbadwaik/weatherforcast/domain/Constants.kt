package com.shrikantbadwaik.weatherforcast.domain

object Constants {
    const val DURATION_4000: Long = 4000L
    const val API_ERROR_RESPONSE_KEY = "ERROR_KEY_HERE"
    const val RETRY = 100

    enum class ActivityCallingState {
        CALL_WEATHER_FORECAST_ACTIVITY
    }

    enum class DialogState {
        GPS_DIALOG, LOCATION_PERMISSION_DIALOG,
        API_ERROR_DIALOG, DEVICE_OFFLINE_DIALOG
    }

    enum class RuntimePermissions {
        LOCATION_PERMISSION
    }

    enum class DeviceState {
        ONLINE, OFFLINE
    }

    enum class Request {
        ON_SUCCESS, ON_FAILURE, ON_COMPLETED
    }
}