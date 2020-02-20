package com.hamidjonhamidov.whoiskhamidjon.di.blog_posts

import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.whoiskhamidjon.di.ViewModelKey
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.viewmodel.BlogPostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BlogViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(BlogPostsViewModel::class)
    abstract fun bindBlogPostsViewModel(blogPostsViewModel: BlogPostsViewModel): ViewModel
}