package com.hamidjonhamidov.whoiskhamidjon.models.skills

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "skills")
data class SkillModel(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name") var name: String = "",

    @SerializedName("image_url")
    @Expose
    @ColumnInfo(name = "image_url") var image_url: String = "",

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description") var description: String = "",

    @SerializedName("percentage")
    @Expose
    @ColumnInfo(name = "percentage") var percentage: String = "0"

) : Parcelable {

    @Ignore
    constructor(skillModel: SkillModel): this(){
        this.id = skillModel.id
        this.description = skillModel.description
        this.name = skillModel.name
        this.image_url = skillModel.image_url
        this.percentage = skillModel.percentage
    }

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as SkillModel

        if (id == other.id &&
            name == other.name &&
            image_url == other.image_url &&
            description == other.description &&
            percentage == other.percentage
        )
            return true
        return false
    }

    override fun toString(): String {
        return """
            id==$id, name=$name, image_url=$image_url, description=$description, percentage=$percentage
        """.trimIndent()
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + image_url.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + percentage.hashCode()
        return result
    }
}































