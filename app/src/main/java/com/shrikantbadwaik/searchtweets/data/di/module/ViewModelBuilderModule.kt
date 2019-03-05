package com.shrikantbadwaik.searchtweets.data.di.module

import android.arch.lifecycle.ViewModelProvider
import com.shrikantbadwaik.searchtweets.domain.factory.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilderModule {
    @Binds
    abstract fun bindViewModelProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory


}