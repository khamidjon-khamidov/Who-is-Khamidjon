package com.hamidjonhamidov.whoiskhamidjon.di

import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.whoiskhamidjon.util.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

}