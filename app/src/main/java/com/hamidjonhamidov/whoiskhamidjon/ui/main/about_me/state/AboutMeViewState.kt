package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state

import android.os.Parcelable
import com.hamidjonhamidov.whoiskhamidjon.models.database.AboutMeModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class AboutMeViewState(

    // this is for AboutMeFragment
    var aboutMeFields: AboutMeFields = AboutMeFields()

) : Parcelable{

    @Parcelize
    data class AboutMeFields(
        var aboutMeModel: AboutMeModel? = null
    ): Parcelable
}