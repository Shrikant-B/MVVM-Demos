package com.shrikantbadwaik.newsheadlines.domain.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.StringRes
import org.koin.core.KoinComponent

class AppUtil constructor(private val context: Context) : KoinComponent {
    companion object {
        fun isMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        fun isDeviceOnline(context: Context?): Boolean {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (cm != null) {
                val activeNetwork = cm.activeNetworkInfo
                activeNetwork != null && activeNetwork.isConnected
            } else false
        }
    }

    fun getString(@StringRes id: Int, vararg args: Any): String = context.getString(id, *args)
}