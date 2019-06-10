package com.shrikantbadwaik.newsheadlines.data.repository

import com.shrikantbadwaik.newsheadlines.BuildConfig
import com.shrikantbadwaik.newsheadlines.data.local.ArticleDao
import com.shrikantbadwaik.newsheadlines.data.local.PreferenceHelper
import com.shrikantbadwaik.newsheadlines.data.remote.NewsApi
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import com.shrikantbadwaik.newsheadlines.domain.model.News
import io.reactivex.Maybe
import io.reactivex.Single

class RepositoryImpl constructor(
    private val prefs: PreferenceHelper, private val api: NewsApi, private val articleDao: ArticleDao
) : Repository {
    override fun getTopHeadlines(country: String, category: String, query: String?): Single<List<Article>> {
        return api.topHeadlines(BuildConfig.API_KEY, country, category, query, 30)
            .flatMapIterable { news: News -> news.articles }
            .doOnNext {
                it.category = category
                it.country = country
                articleDao.addNewsArticle(it)
            }.toList()
    }

    override fun getOfflineArticles(
        search: Boolean, country: String,
        category: String, query: String?
    ): Maybe<List<Article>> {
        return if (search) {
            articleDao.searchNewsArticles(category, country, "%$query%")
        } else {
            articleDao.getNewsArticles(category, country)
        }
    }
}