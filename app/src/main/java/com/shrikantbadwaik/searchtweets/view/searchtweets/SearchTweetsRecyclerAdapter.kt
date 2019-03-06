package com.shrikantbadwaik.searchtweets.view.searchtweets

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shrikantbadwaik.searchtweets.BR
import com.shrikantbadwaik.searchtweets.R
import com.shrikantbadwaik.searchtweets.databinding.LayoutSearchTweetsAdapterContentBinding
import com.shrikantbadwaik.searchtweets.domain.model.Tweet
import com.shrikantbadwaik.searchtweets.domain.util.AppUtil
import javax.inject.Inject

class SearchTweetsRecyclerAdapter @Inject constructor(
    private val appUtil: AppUtil
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val tweets: ArrayList<Tweet> = ArrayList()

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    fun setTweets(tweets: ArrayList<Tweet>?) {
        tweets?.let {
            if (it.isNotEmpty()) {
                this.tweets.clear()
                this.tweets.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val adapterBinding = DataBindingUtil.inflate<LayoutSearchTweetsAdapterContentBinding>(
            LayoutInflater.from(parent.context), R.layout.layout_search_tweets_adapter_content,
            parent, false
        )
        return ContentViewHolder(adapterBinding)
    }

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ContentViewHolder
        viewHolder.bind(tweets[position])
    }

    inner class ContentViewHolder(
        private val binding: LayoutSearchTweetsAdapterContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tweet: Tweet) {
            val viewModel = ViewModel(tweet)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }

        inner class ViewModel(tweet: Tweet) {
            private val profilePic: ObservableField<String> = ObservableField()
            private val twitterUserName: ObservableField<String> = ObservableField()
            private val twitterUserHandle: ObservableField<String> = ObservableField()
            private val tweetText: ObservableField<String> = ObservableField()
            private val retweetCount: ObservableField<String> = ObservableField()
            private val favouriteCount: ObservableField<String> = ObservableField()

            fun getProfilePic() = profilePic

            fun getTwitterUserName() = twitterUserName

            fun getTwitterUserHandle() = twitterUserHandle

            fun getTweetText() = tweetText

            fun getRetweetCount() = retweetCount

            fun getFavouriteCount() = favouriteCount

            init {
                profilePic.set(tweet.twitterUser?.profileImage)
                twitterUserName.set(tweet.twitterUser?.userName)
                twitterUserHandle.set(appUtil.getString(R.string.format01, tweet.twitterUser?.screenName.toString()))
                tweetText.set(tweet.tweetText)
                retweetCount.set(tweet.retweetCount.toString())
                favouriteCount.set(tweet.favouriteCount.toString())
            }
        }
    }
}