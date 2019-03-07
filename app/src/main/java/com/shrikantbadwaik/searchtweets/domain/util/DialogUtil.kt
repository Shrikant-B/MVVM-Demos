package com.shrikantbadwaik.searchtweets.domain.util

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import com.shrikantbadwaik.searchtweets.R
import com.shrikantbadwaik.searchtweets.databinding.LayoutErrorDialogBinding
import com.shrikantbadwaik.searchtweets.databinding.LayoutSortTweetsDialogBinding

object DialogUtil {
    interface DialogListener {
        fun onButtonClicked()
    }

    interface SortTweetDialogListener {
        fun onSortByRetweetClicked(text: String)
        fun onSortByFavouriteClicked(text: String)
        fun onSortByBothClicked(text: String)
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

    fun showSortTweetsDialog(context: Context, listener: SortTweetDialogListener) {
        val dialog = Dialog(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
        val layoutBinding = DataBindingUtil.inflate<LayoutSortTweetsDialogBinding>(
            LayoutInflater.from(context), R.layout.layout_sort_tweets_dialog,
            null, false
        )
        dialog.setCancelable(true)
        dialog.setContentView(layoutBinding.root)
        layoutBinding.btnRetweets.setOnClickListener {
            dialog.dismiss()
            listener.onSortByRetweetClicked(context.getString(R.string.txt_retweets))
        }
        layoutBinding.btnFavourites.setOnClickListener {
            dialog.dismiss()
            listener.onSortByFavouriteClicked(context.getString(R.string.txt_favorites))
        }
        layoutBinding.btnBoth.setOnClickListener {
            dialog.dismiss()
            listener.onSortByBothClicked(context.getString(R.string.txt_both))
        }
        layoutBinding.btnCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}