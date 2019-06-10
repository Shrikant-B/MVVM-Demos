package com.shrikantbadwaik.newsheadlines.view.articledetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.net.Uri
import android.os.Bundle
import com.shrikantbadwaik.newsheadlines.R
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import com.shrikantbadwaik.newsheadlines.domain.util.AppUtil
import com.shrikantbadwaik.newsheadlines.domain.util.Constants
import com.shrikantbadwaik.newsheadlines.domain.util.DateUtil
import org.koin.core.KoinComponent
import org.koin.core.inject

class ArticleDetailsViewModel : ViewModel(), KoinComponent {
    private val appUtil: AppUtil by inject()

    private val articleImage: ObservableField<String> = ObservableField()
    private val articleTitle: ObservableField<String> = ObservableField()
    private val articleAuthor: ObservableField<String> = ObservableField()
    private val articleDate: ObservableField<String> = ObservableField()
    private val articleDescription: ObservableField<String> = ObservableField()
    private val clickEventLiveData: MutableLiveData<String> = MutableLiveData()
    private var webUrl: String? = null

    fun getArticleImage() = articleImage

    fun getArticleTitle() = articleTitle

    fun getArticleAuthor() = articleAuthor

    fun getArticleDate() = articleDate

    fun getArticleDescription() = articleDescription

    fun getClickEventLiveData() = clickEventLiveData

    fun getWebUrl(): Uri {
        var uri = Uri.parse(webUrl)
        if (!webUrl.toString().startsWith("http://") && !webUrl.toString().startsWith("https://")) {
            uri = Uri.parse("http://$webUrl")
        }
        return uri
    }

    fun onOpenBrowserClicked() {
        clickEventLiveData.value = Constants.ClickEventState.OPEN_WEB_BROWSER.name
    }

    fun showArticleDetails(extras: Bundle?) {
        extras?.let { bundle ->
            val article = bundle.getParcelable<Article>(Constants.ARG_ARTICLE)
            article?.let {
                articleImage.set(it.imageUrl)
                articleTitle.set(it.articleTitle)
                if (!article.articleAuthor.isNullOrEmpty()) {
                    articleAuthor.set(appUtil.getString(R.string.txt_article_by, article.articleAuthor))
                } else {
                    articleAuthor.set(appUtil.getString(R.string.txt_article_by, article.source?.sourceName.toString()))
                }
                articleDate.set(
                    appUtil.getString(
                        R.string.txt_published_on, DateUtil.dateToString(
                            DateUtil.MMMddyyyy,
                            DateUtil.stringToDate(DateUtil.yyyyMMddHHmmss, it.publicationDate)
                        ).toString()
                    )
                )
                articleDescription.set(it.articleDescription)
                webUrl = it.articleUrl
            }
        }
    }
}