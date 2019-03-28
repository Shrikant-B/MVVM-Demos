package com.shrikantbadwaik.newsheadlines.data.di.module

import com.shrikantbadwaik.newsheadlines.data.di.annotation.ActivityScope
import com.shrikantbadwaik.newsheadlines.view.articledetails.ArticleDetailsActivity
import com.shrikantbadwaik.newsheadlines.view.splash.SplashActivity
import com.shrikantbadwaik.newsheadlines.view.topheadline.TopHeadlineActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindTopHeadlineActivity(): TopHeadlineActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindArticleDetailsActivity(): ArticleDetailsActivity
}