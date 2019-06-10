package com.shrikantbadwaik.newsheadlines.data.di

import com.shrikantbadwaik.newsheadlines.data.local.PreferenceHelper
import com.shrikantbadwaik.newsheadlines.data.repository.Repository
import com.shrikantbadwaik.newsheadlines.data.repository.RepositoryImpl
import com.shrikantbadwaik.newsheadlines.domain.util.AppUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    single { PreferenceHelper(androidContext()) }

    single<Repository> { RepositoryImpl(get(), get(), get()) }

    single { AppUtil(androidContext()) }
}