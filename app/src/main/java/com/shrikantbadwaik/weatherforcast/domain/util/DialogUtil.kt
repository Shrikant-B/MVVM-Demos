package com.shrikantbadwaik.weatherforcast.domain.util

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.shrikantbadwaik.weatherforcast.R

object DialogUtil {

    fun showErrorDialog(context: Context, message: String, onClickListener: DialogInterface.OnClickListener) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.txt_error)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.txt_ok, onClickListener)
        builder.show()
    }
}