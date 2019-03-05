package com.shrikantbadwaik.searchtweets.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(context: Context) {
    val accessToken = "twitter_access_token"
    private val prefFileName = "twitter-pref-file"
    private val pref: SharedPreferences

    init {
        pref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String?) {
        pref.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String?): String? = pref.getString(key, defValue)
}