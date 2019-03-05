package com.shrikantbadwaik.searchtweets.domain.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.StringRes
import android.util.Base64
import java.io.UnsupportedEncodingException
import javax.inject.Inject

class AppUtil @Inject constructor(private val context: Context) {
    companion object {
        fun isMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        fun isDeviceOnline(context: Context?): Boolean {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (cm != null) {
                val activeNetwork = cm.activeNetworkInfo
                activeNetwork != null && activeNetwork.isConnected
            } else false
        }

        @Throws(UnsupportedEncodingException::class)
        fun base64String(value: String): String {
            return Base64.encodeToString(value.toByteArray(charset("UTF-8")), Base64.NO_WRAP)
        }
    }

    fun getString(@StringRes id: Int, vararg args: Any): String = context.getString(id, *args)
}