package com.shrikantbadwaik.newsheadlines.domain.util

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.databinding.LayoutErrorDialogBinding

object DialogUtil {
    interface DialogListener {
        fun onButtonClicked()
        fun onItemSelected(which: String, name: String)
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

    fun showCountryPickerDialog(context: Context, clickListener: DialogListener?) {
        val builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
        val countries = context.resources.getStringArray(R.array.country)
        builder.setTitle(R.string.txt_select_country)
            .setItems(countries) { dialog, which ->
                clickListener?.onItemSelected(Constants.COUNTRIES[which], countries[which])
                dialog?.dismiss()
            }
        builder.create().show()
    }

    fun showCategoryPickerDialog(context: Context, clickListener: DialogListener?) {
        val builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
        val categories = context.resources.getStringArray(R.array.category)
        builder.setTitle(R.string.txt_select_category)
            .setItems(categories) { dialog, which ->
                clickListener?.onItemSelected(Constants.CATEGORIES[which], categories[which])
                dialog?.dismiss()
            }
        builder.create().show()
    }
}