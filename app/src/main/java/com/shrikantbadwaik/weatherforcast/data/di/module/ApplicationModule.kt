package com.shrikantbadwaik.weatherforcast.data.di.module

import android.app.Application
import android.content.Context
import com.shrikantbadwaik.weatherforcast.data.repository.Repository
import com.shrikantbadwaik.weatherforcast.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
abstract class ApplicationModule {
    @Binds
    abstract fun bindApplication(application: Application): Context

    @Binds
    abstract fun bindRepository(repository: RepositoryImpl): Repository
}