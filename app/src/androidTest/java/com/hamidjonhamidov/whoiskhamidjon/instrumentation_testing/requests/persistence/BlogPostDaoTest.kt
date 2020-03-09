package com.hamidjonhamidov.whoiskhamidjon.instrumentation_testing.requests.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import com.hamidjonhamidov.whoiskhamidjon.util.LiveDataUtil
import com.hamidjonhamidov.whoiskhamidjon.util.TestUtil
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class BlogPostDaoTest : AppDatabaseTest(){

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun insertOrReplaceGetBlogPosts(){
        // insert blogposts
        getBlogPostDao().insertOrReplaceBlogPost(TestUtil.BLOGPOST_TEMPLATE_1)
        getBlogPostDao().insertOrReplaceBlogPost(TestUtil.BLOGPOST_TEMPLATE_2)

        // read blogposts
        val liveDataUtil = LiveDataUtil<List<BlogPostModel>>()
        val insertedList = liveDataUtil
            .getValue(getBlogPostDao().getBlogPosts())

        Assert.assertEquals(insertedList!!.size, 2)
        Assert.assertEquals(insertedList[0], TestUtil.BLOGPOST_TEMPLATE_1)
        Assert.assertEquals(insertedList[1], TestUtil.BLOGPOST_TEMPLATE_2)
    }
}
