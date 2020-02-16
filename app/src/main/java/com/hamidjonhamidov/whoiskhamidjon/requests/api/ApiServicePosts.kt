package com.hamidjonhamidov.whoiskhamidjon.requests.api

import androidx.lifecycle.LiveData
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import com.hamidjonhamidov.whoiskhamidjon.util.GenericNetworkResponse
import retrofit2.http.GET

interface ApiServicePosts {

    @GET("posts")
    fun getPosts(): LiveData<GenericNetworkResponse<BlogPostModel>>
}