package com.hamidjonhamidov.whoiskhamidjon.models.posts

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "posts")
class BlogPostModel(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "userId")
    var userId: Int? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "body")
    var body: String? = null

) : Parcelable {

    @Ignore
    constructor(blogPostModel: BlogPostModel) : this() {
        this.id = blogPostModel.id
        this.userId = blogPostModel.userId
        this.title = blogPostModel.title
        this.body = blogPostModel.body
    }

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass)
            return false

        other as BlogPostModel

        if (
            id == other.id &&
            userId == other.userId &&
            title == other.title &&
            body == other.body
        )
            return true

        return false
    }

    override fun toString(): String {
        return "id=$id, userId=$userId, title=$title, body=$body"
    }
}