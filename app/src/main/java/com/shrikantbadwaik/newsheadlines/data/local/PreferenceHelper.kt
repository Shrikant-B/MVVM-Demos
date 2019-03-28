package com.shrikantbadwaik.newsheadlines.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(context: Context) {
    private val prefFileName = "newsapp-pref-file"
    private val pref: SharedPreferences

    init {
        pref = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String?) {
        pref.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String?): String? = pref.getString(key, defValue)
}