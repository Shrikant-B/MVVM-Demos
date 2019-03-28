package com.shrikantbadwaik.newsheadlines.data.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shrikantbadwaik.newsheadlines.data.di.annotation.ViewModelKey
import com.shrikantbadwaik.newsheadlines.domain.factory.ViewModelProviderFactory
import com.shrikantbadwaik.newsheadlines.view.splash.SplashViewModel
import com.shrikantbadwaik.newsheadlines.view.topheadline.TopHeadlineViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilderModule {
    @Binds
    abstract fun bindViewModelProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopHeadlineViewModel::class)
    abstract fun bindTopHeadlineViewModel(viewModel: TopHeadlineViewModel): ViewModel
}