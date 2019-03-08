package com.shrikantbadwaik.searchtweets.domain.util

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.shrikantbadwaik.searchtweets.R
import com.shrikantbadwaik.searchtweets.domain.model.Tweet
import com.shrikantbadwaik.searchtweets.view.searchtweets.SearchTweetsRecyclerAdapter


object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["android:imageUrl"])
    fun setImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .error(R.drawable.ic_twitter_logo)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(imageView.width, imageView.height)
                ).load(it).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["android:mostRecentTweets"])
    fun showMostRecentTweets(recyclerView: RecyclerView, tweets: ArrayList<Tweet>?) {
        val adapter = recyclerView.adapter as SearchTweetsRecyclerAdapter?
        adapter?.setTweets(tweets)
    }

    @JvmStatic
    @BindingAdapter(value = ["android:mediaType", "android:mediaUrl"])
    fun showTweetImage(imageView: AppCompatImageView, mediaType: String?, mediaUrl: String?) {
        when (mediaType) {
            Constants.MEDIA_TYPE_PHOTO -> {
                mediaUrl?.let {
                    Glide.with(imageView.context)
                        .applyDefaultRequestOptions(
                            RequestOptions()
                                .error(R.drawable.ic_twitter_logo)
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .encodeFormat(Bitmap.CompressFormat.PNG)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .override(imageView.width, imageView.height)
                        ).load(it).into(imageView)
                }
            }
        }
    }
}