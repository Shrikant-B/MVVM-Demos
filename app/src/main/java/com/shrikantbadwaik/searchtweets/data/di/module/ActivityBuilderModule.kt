package com.shrikantbadwaik.searchtweets.data.di.module

import com.shrikantbadwaik.searchtweets.data.di.annotation.ActivityScope
import com.shrikantbadwaik.searchtweets.view.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity
}