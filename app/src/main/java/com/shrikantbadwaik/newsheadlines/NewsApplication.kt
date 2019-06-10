package com.shrikantbadwaik.newsheadlines

import android.app.Application
import com.shrikantbadwaik.newsheadlines.data.di.applicationModule
import com.shrikantbadwaik.newsheadlines.data.di.networkModule
import com.shrikantbadwaik.newsheadlines.data.di.roomModule
import com.shrikantbadwaik.newsheadlines.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            androidFileProperties()
            modules(listOf(applicationModule, viewModelModule, networkModule, roomModule))
        }
    }
}