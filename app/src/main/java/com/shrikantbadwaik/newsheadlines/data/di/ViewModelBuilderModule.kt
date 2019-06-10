package com.shrikantbadwaik.newsheadlines.data.di

import com.shrikantbadwaik.newsheadlines.view.articledetails.ArticleDetailsViewModel
import com.shrikantbadwaik.newsheadlines.view.splash.SplashViewModel
import com.shrikantbadwaik.newsheadlines.view.topheadline.TopHeadlineViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }

    viewModel { TopHeadlineViewModel() }

    viewModel { ArticleDetailsViewModel() }
}