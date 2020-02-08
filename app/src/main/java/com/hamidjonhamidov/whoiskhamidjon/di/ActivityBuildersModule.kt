package com.hamidjonhamidov.whoiskhamidjon.di

import com.hamidjonhamidov.whoiskhamidjon.di.main.MainFragmentBuildersModule
import com.hamidjonhamidov.whoiskhamidjon.di.main.MainModule
import com.hamidjonhamidov.whoiskhamidjon.di.main.MainScope
import com.hamidjonhamidov.whoiskhamidjon.di.main.MainViewModelModule
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [MainModule::class, MainViewModelModule::class, MainFragmentBuildersModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

    // here another scopes come
}