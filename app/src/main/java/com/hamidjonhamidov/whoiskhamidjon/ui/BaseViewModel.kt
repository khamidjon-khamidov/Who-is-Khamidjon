package com.hamidjonhamidov.whoiskhamidjon.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<StateEvent, ViewState> : ViewModel() {

    private val TAG = "AppDebug"

    protected val _stateEvent: MutableLiveData<StateEvent> = MutableLiveData()
    protected val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val dataState: LiveData<DataState<ViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent: StateEvent ->
            stateEvent?.let {
                Log.d(TAG, "BaseViewModel: dataState: Changedohhh")

                handleStateEvent(stateEvent)
            }
        }

    fun setStateEvent(event: StateEvent) {
        Log.d(TAG, "BaseViewModel: setStateEvent: event=$event")
        _stateEvent.value = event
    }

    fun getCurrentViewStateOrNew(): ViewState {
        Log.d(TAG, "BaseViewModel: getCurrentViewStateOrNew: ")
        val value = viewState.value?.let {
            Log.d(TAG, "BaseViewModel: getCurrentViewStateOrNew: current one")
            it
        } ?: initNewViewState().also {
            Log.d(TAG, "BaseViewModel: getCurrentViewStateOrNew: new one")
        }

        return value
    }

    fun setViewState(viewState: ViewState) {
        Log.d(TAG, "BaseViewModel: setViewState: called")
        _viewState.value = viewState
    }

    abstract fun handleStateEvent(stateEvent: StateEvent): LiveData<DataState<ViewState>>

    abstract fun initNewViewState(): ViewState
}























