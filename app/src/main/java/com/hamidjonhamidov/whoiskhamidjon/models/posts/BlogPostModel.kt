package com.hamidjonhamidov.whoiskhamidjon.models.posts

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
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

): Parcelable{

    override fun toString(): String {
        return "id=$id, userId=$userId, title=$title, body=$body"
    }
}