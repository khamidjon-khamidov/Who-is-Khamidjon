package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import com.hamidjonhamidov.whoiskhamidjon.repository.MainRepository
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
    val mainRepository: MainRepository
) : BaseViewModel<AboutMeStateEvent, AboutMeViewState>() {

    private val TAG = "AppDebug"

    override fun handleStateEvent(stateEvent: AboutMeStateEvent): LiveData<DataState<AboutMeViewState>> {
        return when (stateEvent) {
            is GetAboutMeStateEvent -> {
//                mainRepository.getAboutMeInfo(sessionManager.isConnectedToTheInternet())

                // this is for testing
                mainRepository.getAboutMeInfoForTesting()
            }

            is None -> {
                // for testing
                mainRepository.getAboutMeInfoForTesting()

                AbsentLiveData.create()
            }
        }
    }

    fun setAboutMeFields(aboutMeFields: AboutMeFields): Boolean {
        val update = getCurrentViewStateOrNew()
        if (update.aboutMeFields.aboutMeModel == aboutMeFields.aboutMeModel)
            return false

        Log.d(TAG, "AboutMeViewModel: setAboutMeFields: called")

        update.aboutMeFields = aboutMeFields
        setViewState(update)
        return true
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "AboutMeViewModel: onCleared: called")
        cancelActiveJobs()
    }

    fun cancelActiveJobs() {
        mainRepository.cancelActiveJobs()
        handlePendingData() // hide progress bar
    }

    fun handlePendingData() {
        Log.d(TAG, "AboutMeViewModel: handlePendingData: called")
        setStateEvent(None())
    }

    override fun initNewViewState(): AboutMeViewState {
        Log.d(TAG, "AboutMeViewModel: initNewViewState: called")
        return AboutMeViewState()
    }
}








