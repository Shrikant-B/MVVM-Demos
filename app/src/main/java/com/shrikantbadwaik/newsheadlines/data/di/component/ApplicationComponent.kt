package com.shrikantbadwaik.newsheadlines.data.di.component

import android.app.Application
import com.shrikantbadwaik.newsheadlines.NewsApplication
import com.shrikantbadwaik.newsheadlines.data.di.module.ActivityBuilderModule
import com.shrikantbadwaik.newsheadlines.data.di.module.ApplicationModule
import com.shrikantbadwaik.newsheadlines.data.di.module.ViewModelBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ApplicationModule::class,
        ActivityBuilderModule::class, ViewModelBuilderModule::class]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: NewsApplication)
}