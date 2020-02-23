package com.hamidjonhamidov.whoiskhamidjon.requests.api

import androidx.lifecycle.LiveData
import androidx.room.Delete
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import com.hamidjonhamidov.whoiskhamidjon.util.GenericNetworkResponse
import retrofit2.http.*

interface ApiBlogPostsService {

    @GET("posts")
    fun getPosts(): LiveData<GenericNetworkResponse<List<BlogPostModel>>>

    @POST("posts")
    fun postBlogPost(
        @Query("title") title: String,
        @Query("body") body: String,
        @Query("userId") userId: Int
    ): LiveData<GenericNetworkResponse<BlogPostModel>>

    @DELETE("posts/{id}")
    fun deleteBlogPost(
        @Path("id") id: Int
    ) : LiveData<GenericNetworkResponse<BlogPostModel>>
}