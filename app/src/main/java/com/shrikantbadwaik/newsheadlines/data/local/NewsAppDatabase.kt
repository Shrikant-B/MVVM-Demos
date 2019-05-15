package com.shrikantbadwaik.newsheadlines.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.shrikantbadwaik.newsheadlines.data.local.converters.ArticleSourceTypeConverter
import com.shrikantbadwaik.newsheadlines.domain.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(ArticleSourceTypeConverter::class)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}