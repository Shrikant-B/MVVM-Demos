package com.shrikantbadwaik.searchtweets.domain.widget

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.shrikantbadwaik.searchtweets.R

class CustomFontTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView)
            val fontName = a.getString(R.styleable.CustomFontTextView_customFontName)

            try {
                fontName?.let {
                    val myTypeface = Typeface.createFromAsset(context.assets, "fonts/$it")
                    typeface = myTypeface
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            a.recycle()
        }
    }
}