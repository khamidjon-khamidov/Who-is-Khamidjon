package com.hamidjonhamidov.whoiskhamidjon.ui.posts.state

import android.os.Parcelable
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class BlogsPostsViewState(
    // this is for BlogPostsFragment
    var blogPostsFields: BlogPostsFields = BlogPostsFields()
): Parcelable{

    @Parcelize
    data class BlogPostsFields(
        var blogPostsList: List<BlogPostModel>? = null
    ): Parcelable
}


