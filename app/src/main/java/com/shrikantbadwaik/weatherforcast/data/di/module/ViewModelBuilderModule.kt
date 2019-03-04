package com.shrikantbadwaik.weatherforcast.data.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.shrikantbadwaik.weatherforcast.data.di.annotation.ViewModelKey
import com.shrikantbadwaik.weatherforcast.domain.factory.ViewModelProviderFactory
import com.shrikantbadwaik.weatherforcast.view.splash.SplashViewModel
import com.shrikantbadwaik.weatherforcast.view.weatherforecast.WeatherForecastViewModel
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
    @ViewModelKey(WeatherForecastViewModel::class)
    abstract fun bindWeatherForecastViewModel(viewModel: WeatherForecastViewModel): ViewModel
}