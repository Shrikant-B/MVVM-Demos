package com.shrikantbadwaik.newsheadlines.view.topheadline

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shrikantbadwaik.newsheadlines.BR
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.databinding.LayoutTopHeadlineAdapterContentBinding
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import com.shrikantbadwaik.newsheadlines.domain.util.DateUtil

class TopHeadlineRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val articles: ArrayList<Article> = ArrayList()
    private var callback: AdapterCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val adapterBinding = DataBindingUtil.inflate<LayoutTopHeadlineAdapterContentBinding>(
            LayoutInflater.from(parent.context), R.layout.layout_top_headline_adapter_content,
            parent, false
        )
        return ContentViewHolder(adapterBinding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ContentViewHolder
        viewHolder.bind(articles[position])
        viewHolder.itemView.setOnClickListener { callback?.onArticleClicked(articles[position]) }
    }

    fun setCallback(callback: AdapterCallback) {
        this.callback = callback
    }

    fun setArticles(articles: ArrayList<Article>?) {
        articles?.let {
            if (it.isNotEmpty()) {
                this.articles.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    fun clear() {
        articles.clear()
        notifyDataSetChanged()
    }

    class ContentViewHolder(
        private val binding: LayoutTopHeadlineAdapterContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            val viewModel = ContentViewHolderVM(article)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }

    class ContentViewHolderVM(article: Article) {
        private val articleAuthor: ObservableField<String> = ObservableField()
        private val articleTitle: ObservableField<String> = ObservableField()
        private val articleDate: ObservableField<String> = ObservableField()
        private val articleImage: ObservableField<String> = ObservableField()

        init {
            if (!article.articleAuthor.isNullOrEmpty()) {
                articleAuthor.set(article.articleAuthor)
            } else articleAuthor.set(article.source?.sourceName)
            articleTitle.set(article.articleTitle)
            articleDate.set(
                DateUtil.durationAgo(
                    DateUtil.stringToDate(
                        DateUtil.yyyyMMddHHmmss, article.publicationDate
                    )
                )
            )
            articleImage.set(article.imageUrl)
        }

        fun getArticleAuthor() = articleAuthor
        fun getArticleTitle() = articleTitle
        fun getArticleDate() = articleDate
        fun getArticleImage() = articleImage
    }

    interface AdapterCallback {
        fun onArticleClicked(article: Article)
    }
}