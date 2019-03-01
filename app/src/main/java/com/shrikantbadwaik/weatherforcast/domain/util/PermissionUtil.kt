package com.shrikantbadwaik.weatherforcast.domain.util

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

object PermissionUtil {
    const val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
    const val PERMISSION_DENIED = PackageManager.PERMISSION_DENIED
    const val PERMISSION_REQUEST_CODE_LOCATION = 100
    val LOCATION_PERMISSION =
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermission(activity: Activity, permissions: Array<String>, requestCode: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, requestCode)
            0
        } else -1
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(context: Context, permissions: Array<String>): Boolean {
        var isGranted = true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            isGranted = false
        } else {
            for (permission in permissions) {
                if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false
                    break
                }
            }
        }
        return isGranted
    }
}