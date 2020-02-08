package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state

import kotlinx.android.parcel.Parcelize


sealed class AboutMeStateEvent{
    class GetAboutMeStateEvent(): AboutMeStateEvent()

    class None: AboutMeStateEvent()
}