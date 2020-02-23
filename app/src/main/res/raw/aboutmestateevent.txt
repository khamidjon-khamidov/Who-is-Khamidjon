package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state

sealed class AboutMeStateEvent{
    class GetAboutMeStateEvent(): AboutMeStateEvent()

    class None: AboutMeStateEvent()
}