package com.hamidjonhamidov.whoiskhamidjon.requests.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel

@Dao
interface BlogPostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceBlogPost(blogPostModel: BlogPostModel)

    @Query("SELECT * FROM posts")
    fun getBlogPosts(): LiveData<List<BlogPostModel>>
}