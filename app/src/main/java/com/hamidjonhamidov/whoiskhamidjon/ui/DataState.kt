package com.hamidjonhamidov.whoiskhamidjon.ui

import android.util.Log

data class DataState<T>(
    var error: Event<StateError>? = null,
    var loading: Loading = Loading(false),
    var data: Data<T>? = null
) {

    companion object {

        private val TAG = "AppDebug"

        fun <T> error(response: MyResponse): DataState<T> {
            Log.d(TAG, "DataState: error: called")
            return DataState(
                error = Event(StateError(response)),
                loading = Loading(false),
                data = null
            )
        }

        fun <T> loading(isLoading: Boolean = false, cachedData: T? = null):
                DataState<T> {
//            Log.d(TAG, "DataState: loading: called")

            return DataState(
                error = null,
                loading = Loading(isLoading),
                data = Data(
                    dataReceived = Event.dataEvent(cachedData),
                    responseReceived = null
                )
            )
        }

        fun <T> data(data: T? = null, response: MyResponse? = null)
                : DataState<T> {
//            Log.d(TAG, "DataState: data: called")

            return DataState(
                error = null,
                loading = Loading(false),
                data = Data(
                    dataReceived = Event.dataEvent(data),
                    responseReceived = Event.responseEvent(response)
                )
            )
        }
    }
}


















