package com.shrikantbadwaik.newsheadlines.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.shrikantbadwaik.newsheadlines.domain.model.Article
import io.reactivex.Maybe

@Dao
interface ArticleDao {
    @Query("SELECT * from newsArticle where category = :category AND country = :country")
    fun getNewsArticles(category: String, country: String): Maybe<List<Article>>

    @Query("SELECT * from newsArticle where category = :category AND country = :country AND (articleTitle LIKE :query OR articleContent LIKE :query OR articleDescription LIKE :query)")
    fun searchNewsArticles(category: String, country: String, query: String?): Maybe<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewsArticle(article: Article): Long
}