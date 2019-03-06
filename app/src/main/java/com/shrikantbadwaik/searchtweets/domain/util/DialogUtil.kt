package com.shrikantbadwaik.searchtweets.domain.util

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import com.shrikantbadwaik.searchtweets.R
import com.shrikantbadwaik.searchtweets.databinding.LayoutErrorDialogBinding

object DialogUtil {
    interface DialogListener {
        fun onButtonClicked()
    }

    fun showErrorDialog(context: Context, message: String?, okClickListener: DialogListener?) {
        val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
        val layoutBinding = DataBindingUtil.inflate<LayoutErrorDialogBinding>(
            LayoutInflater.from(context), R.layout.layout_error_dialog,
            null, false
        )
        dialog.setContentView(layoutBinding.root)
        layoutBinding.txtErrorMessage.text = message
        layoutBinding.btnOk.setOnClickListener {
            dialog.dismiss()
            okClickListener?.onButtonClicked()
        }
        dialog.show()
    }
}