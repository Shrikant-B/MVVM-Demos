package com.shrikantbadwaik.newsheadlines.data.di

import com.shrikantbadwaik.newsheadlines.NewsApplication
import com.shrikantbadwaik.newsheadlines.data.di.component.DaggerApplicationComponent

object ApplicationInjector {
    fun inject(application: NewsApplication) {
        DaggerApplicationComponent.builder()
            .application(application).build()
            .inject(application)
    }
}