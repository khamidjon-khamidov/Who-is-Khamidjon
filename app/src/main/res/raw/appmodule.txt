package com.hamidjonhamidov.whoiskhamidjon.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AppDatabase
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AppDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDb(app: Application) =
        Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions =
        RequestOptions
            .placeholderOf(R.drawable.profile_img_src)

    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ) =
        Glide.with(application)
            .setDefaultRequestOptions(requestOptions)

}