package com.hamidjonhamidov.whoiskhamidjon.ui

import android.util.Log
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState

data class Loading(val isLoading: Boolean)
data class Data<T>(val dataReceived: Event<T>?, var responseReceived: Event<MyResponse>?)
data class StateError(val myResponse: MyResponse)

data class MyResponse(var message: String?, val responseType: ResponseType)
sealed class ResponseType {

    class Snackbar : ResponseType()

    class Dialog : ResponseType()

    class None : ResponseType()
}

open class Event<out T>(private val content: T) {

    private val TAG = "AppDebug"

    init {
        if (content is AboutMeViewState)
            Log.d(TAG, "Event: init: content = ${content.aboutMeFields.aboutMeModel}")
    }

    var hasBeenHandled = false
        private set // allow external read only

    fun getContentIfNotHandled(): T? {

        return if (hasBeenHandled) null else {

            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content

    companion object {
        // we don't want an event if the data is null

        fun <T> dataEvent(data: T?) = if (data == null) null else Event(data)


        // we don't want an event if the response is null

        fun responseEvent(response: MyResponse?) = if (response == null) null else Event(response)

    }
}




















