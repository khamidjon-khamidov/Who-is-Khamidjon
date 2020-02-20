package com.hamidjonhamidov.whoiskhamidjon.di.blog_posts

import com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts.BlogPostsFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts.DeletePostFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts.UpdatePostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BlogFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeBlogPostsFragment(): BlogPostsFragment

    @ContributesAndroidInjector()
    abstract fun contributeDeletePostFragment(): DeletePostFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateFragment(): UpdatePostFragment
}