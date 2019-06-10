package com.shrikantbadwaik.newsheadlines.data.di

import com.shrikantbadwaik.newsheadlines.data.local.NewsAppDatabase
import org.koin.dsl.module

val roomModule = module {
    single {
        return@single NewsAppDatabase.getInstance(get()).articleDao()
    }
}