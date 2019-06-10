package com.shrikantbadwaik.newsheadlines.data.local

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.shrikantbadwaik.newsheadlines.data.local.converters.ArticleSourceTypeConverter
import com.shrikantbadwaik.newsheadlines.domain.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(ArticleSourceTypeConverter::class)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        private const val DATABASE_NAME = "NewsApp.db"

        // For Singleton instantiation
        @Volatile
        private var instance: NewsAppDatabase? = null

        fun getInstance(context: Application): NewsAppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): NewsAppDatabase {
            return Room.databaseBuilder(
                context, NewsAppDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries().build()
        }
    }
}