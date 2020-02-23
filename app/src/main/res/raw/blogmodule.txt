package com.hamidjonhamidov.whoiskhamidjon.di.blog_posts

import com.codingwithmitch.openapi.util.LiveDataCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hamidjonhamidov.whoiskhamidjon.repository.BlogRepository
import com.hamidjonhamidov.whoiskhamidjon.requests.api.ApiBlogPostsService
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AppDatabase
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.BlogPostsDao
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.BlogPostsConstants.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class BlogModule {

    @BlogPostsScope
    @Provides
    fun provideApiBlogPostsService(retrofitBuilder: Retrofit.Builder): ApiBlogPostsService{
        return retrofitBuilder
            .build()
            .create(ApiBlogPostsService::class.java)
    }

    @BlogPostsScope
    @Provides
    fun provideBlogRepository(
        apiBlogPostsService: ApiBlogPostsService,
        blogPostsDao: BlogPostsDao,
        sessionManager: SessionManager
    ): BlogRepository =
        BlogRepository(apiBlogPostsService, sessionManager, blogPostsDao)

    @BlogPostsScope
    @Provides
    fun provideBlogPostsDao(db: AppDatabase) = db.getBlogPostsDao()

    @BlogPostsScope
    @Provides
    fun providesRetrofitBuilder(gsonBuilder: Gson) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())

    @BlogPostsScope
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
}


















