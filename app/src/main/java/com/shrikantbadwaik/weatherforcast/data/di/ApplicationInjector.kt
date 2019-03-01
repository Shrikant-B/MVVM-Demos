package com.shrikantbadwaik.weatherforcast.data.di

import com.shrikantbadwaik.weatherforcast.WeatherForecastApplication
import com.shrikantbadwaik.weatherforcast.data.di.component.DaggerApplicationComponent

object ApplicationInjector {
    fun inject(application: WeatherForecastApplication) {
        DaggerApplicationComponent.builder()
            .application(application).build()
            .inject(application)
    }
}