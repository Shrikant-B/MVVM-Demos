package com.shrikantbadwaik.searchtweets.data.di

import com.shrikantbadwaik.searchtweets.TwitterApplication
import com.shrikantbadwaik.searchtweets.data.di.component.DaggerApplicationComponent

object ApplicationInjector {
    fun inject(application: TwitterApplication) {
        DaggerApplicationComponent.builder()
            .application(application).build()
            .inject(application)
    }
}