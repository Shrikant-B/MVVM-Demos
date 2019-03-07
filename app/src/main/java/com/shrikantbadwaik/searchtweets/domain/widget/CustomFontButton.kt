package com.shrikantbadwaik.searchtweets.domain.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import com.shrikantbadwaik.searchtweets.R

class CustomFontButton : AppCompatButton {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(attrs: AttributeSet?) {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont)
            val fontName = a.getString(R.styleable.CustomFont_customFontName)

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