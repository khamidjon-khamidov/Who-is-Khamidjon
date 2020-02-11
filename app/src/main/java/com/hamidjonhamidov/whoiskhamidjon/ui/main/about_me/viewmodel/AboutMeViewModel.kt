package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.hamidjonhamidov.whoiskhamidjon.repository.AboutMeRepository
import com.hamidjonhamidov.whoiskhamidjon.ui.BaseViewModel
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent.GetAboutMeStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent.None
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState.*
import com.hamidjonhamidov.whoiskhamidjon.util.AbsentLiveData
import javax.inject.Inject

class AboutMeViewModel
@Inject
constructor(
//    val sessionManager: SessionManager,
    val aboutMeRepository: AboutMeRepository
): BaseViewModel<AboutMeStateEvent, AboutMeViewState>(){

    private val TAG = "AppDebug"

    override fun handleStateEvent(stateEvent: AboutMeStateEvent): LiveData<DataState<AboutMeViewState>> {
        return when(stateEvent){
            is GetAboutMeStateEvent -> {
                Log.d(TAG, "AboutMeViewModel: handleStateEvent: GetAboutMeStateEvent")
                aboutMeRepository.getAboutMeInfo()
            }

            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setAboutMeFields(aboutMeFields: AboutMeFields){
        val update = getCurrentViewStateOrNew()
        if(update.aboutMeFields.aboutMeModel == aboutMeFields.aboutMeModel) return

        Log.d(TAG, "AboutMeViewModel: setAboutMeFields: called")

        update.aboutMeFields = aboutMeFields
        setViewState(update)

    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

    fun cancelActiveJobs(){
        aboutMeRepository.cancelActiveJobs()
        handlePendingData() // hide progress bar
    }

    fun handlePendingData(){
        Log.d(TAG, "AboutMeViewModel: handlePendingData: called")
        setStateEvent(None())
    }

    override fun initNewViewState(): AboutMeViewState {
        return AboutMeViewState()
    }
}








