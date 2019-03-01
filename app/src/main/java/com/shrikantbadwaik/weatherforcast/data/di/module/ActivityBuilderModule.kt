package com.shrikantbadwaik.weatherforcast.data.di.module

import com.shrikantbadwaik.weatherforcast.MainActivity
import com.shrikantbadwaik.weatherforcast.data.di.annotation.ActivityScope
import com.shrikantbadwaik.weatherforcast.view.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}