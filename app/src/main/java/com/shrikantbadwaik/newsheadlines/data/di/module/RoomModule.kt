package com.shrikantbadwaik.newsheadlines.data.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.shrikantbadwaik.newsheadlines.data.local.ArticleDao
import com.shrikantbadwaik.newsheadlines.data.local.NewsAppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun newsAppDatabase(context: Context): NewsAppDatabase {
        return Room.databaseBuilder(context, NewsAppDatabase::class.java, "NewsApp.db").build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: NewsAppDatabase): ArticleDao {
        return db.articleDao()
    }
}