package com.hamidjonhamidov.whoiskhamidjon.di

import android.app.Application
import android.content.Context
import com.hamidjonhamidov.whoiskhamidjon.BaseApplication
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {

    // must be here b/c injecting into abstract class
    val sessionManager: SessionManager

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)
}