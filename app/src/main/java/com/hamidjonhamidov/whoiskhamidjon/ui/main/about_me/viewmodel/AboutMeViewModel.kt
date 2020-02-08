package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.hamidjonhamidov.whoiskhamidjon.repository.AboutMeRepository
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
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
    val sessionManager: SessionManager,
    val aboutMeRepository: AboutMeRepository
): BaseViewModel<AboutMeStateEvent, AboutMeViewState>(){

    override fun handleStateEvent(stateEvent: AboutMeStateEvent): LiveData<DataState<AboutMeViewState>> {
        return when(stateEvent){
            is GetAboutMeStateEvent -> {
                 aboutMeRepository.getAboutMeInfo()
            }

            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setAboutMeFields(aboutMeFields: AboutMeFields){
        val update = getCurrentViewStateOrNew()
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
        setStateEvent(None())
    }

    override fun initNewViewState(): AboutMeViewState {
        return AboutMeViewState()
    }


}








