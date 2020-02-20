package com.hamidjonhamidov.whoiskhamidjon.ui.posts.state

sealed class BlogPostsStateEvent{

    class GetBlogListStateEvent(): BlogPostsStateEvent()

    class PostNewBlogStateEvent(val userId: Int, val title: String, val body: String): BlogPostsStateEvent()

    class None: BlogPostsStateEvent()
}