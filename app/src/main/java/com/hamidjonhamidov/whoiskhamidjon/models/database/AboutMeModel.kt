package com.hamidjonhamidov.whoiskhamidjon.models.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "about_me")
class AboutMeModel(

    @SerializedName("pk")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk") var pk: Int = -1,

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email") var email: String="Could'nt load",

    @SerializedName("address")
    @Expose
    @ColumnInfo(name = "address") var address: String="Could'nt load",

    @SerializedName("phone_number")
    @Expose
    @ColumnInfo(name = "phone_number") var phone_number: String="Could'nt load",

    @SerializedName("tuit")
    @Expose
    @ColumnInfo(name = "tuit") var tuit: String="Could'nt load",

    @SerializedName("south_bank")
    @Expose
    @ColumnInfo(name = "south_bank") var south_bank: String="Could'nt load"

): Parcelable{
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass) return false

        other as AboutMeModel

        if(pk == other.pk && email == other.email && address == other.address)
            return true

        return false
    }

    fun convertUniStrToArrayList(someStr: String): ArrayList<String> {

        if(someStr.isEmpty()) return ArrayList()

        var str = someStr
        str = str.removeRange(0, 1)
        str = str.removeRange(str.length - 1, str.length)



        return str.trim().splitToSequence(',', ' ')
            .filter { it.isNotEmpty() }
            .toCollection(ArrayList())
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return """
            pk=$pk, email=$email, address=$address, phoneNumber=$phone_number, tuit=$tuit, southBank=$south_bank
        """.trimIndent()
    }
}















