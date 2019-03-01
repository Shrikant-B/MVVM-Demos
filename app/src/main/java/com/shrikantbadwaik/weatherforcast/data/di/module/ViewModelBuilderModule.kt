package com.shrikantbadwaik.weatherforcast.data.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shrikantbadwaik.weatherforcast.data.di.annotation.ViewModelKey
import com.shrikantbadwaik.weatherforcast.domain.ViewModelProviderFactory
import com.shrikantbadwaik.weatherforcast.view.main.MainViewModel
import com.shrikantbadwaik.weatherforcast.view.splash.SplashViewModel
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
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}