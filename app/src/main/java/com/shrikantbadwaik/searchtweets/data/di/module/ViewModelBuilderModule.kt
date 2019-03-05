package com.shrikantbadwaik.searchtweets.data.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shrikantbadwaik.searchtweets.data.di.annotation.ViewModelKey
import com.shrikantbadwaik.searchtweets.domain.factory.ViewModelProviderFactory
import com.shrikantbadwaik.searchtweets.view.searchtweets.SearchTweetsViewModel
import com.shrikantbadwaik.searchtweets.view.splash.SplashViewModel
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
    @ViewModelKey(SearchTweetsViewModel::class)
    abstract fun bindSearchTweetsViewModel(viewModel: SearchTweetsViewModel): ViewModel
}