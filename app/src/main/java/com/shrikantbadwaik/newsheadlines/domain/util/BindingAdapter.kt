package com.shrikantbadwaik.newsheadlines.domain.util

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import com.shrikantbadwaik.newsheadlines.view.topheadline.TopHeadlineRecyclerAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .error(R.drawable.img_news)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(imageView.width, imageView.height)
                ).load(it).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                    ): Boolean {
                        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: Target<Drawable>?,
                        dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        return false
                    }
                }).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("android:newsArticles")
    fun showArticles(recyclerView: RecyclerView, articles: ArrayList<Article>?) {
        val adapter = recyclerView.adapter as TopHeadlineRecyclerAdapter?
        adapter?.setArticles(articles)
    }
}