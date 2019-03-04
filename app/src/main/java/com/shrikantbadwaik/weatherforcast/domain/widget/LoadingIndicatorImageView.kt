package com.shrikantbadwaik.weatherforcast.domain.widget

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.shrikantbadwaik.weatherforcast.R

class LoadingIndicatorImageView : AppCompatImageView {
    private val rotationDuration = 800L
    private val imageResourceId = R.drawable.ic_loading

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attr: AttributeSet?) : super(context, attr)
    constructor(context: Context?, attr: AttributeSet?, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        setImageResource(imageResourceId)
        startAnimation()
    }

    private fun startAnimation() {
        clearAnimation()

        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = rotationDuration
        rotate.repeatCount = Animation.INFINITE
        startAnimation(rotate)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)

        if (visibility == View.VISIBLE) {
            startAnimation()
        } else clearAnimation()
    }
}