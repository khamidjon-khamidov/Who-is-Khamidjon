package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.whoiskhamidjon.repository.MainRepository
import com.hamidjonhamidov.whoiskhamidjon.ui.BaseViewModel
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsStateEvent.GetSkillsListStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsViewState
import com.hamidjonhamidov.whoiskhamidjon.util.AbsentLiveData
import javax.inject.Inject

class SkillsViewModel
@Inject
constructor(
    val mainRepository: MainRepository
): BaseViewModel<SkillsStateEvent, SkillsViewState>(){

    private val TAG = "AppDebug"

    override fun handleStateEvent(stateEvent: SkillsStateEvent): LiveData<DataState<SkillsViewState>> {
        return when(stateEvent){
            is GetSkillsListStateEvent -> {
                Log.d(TAG, "SkillsViewModel: handleStateEvent: ")
                mainRepository.getSkillsList()
            }

            is SkillsStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): SkillsViewState {
        return SkillsViewState()
    }

    fun setSkillsListFields(skillsListFields: SkillsViewState.SkillsListFields){
        val update = getCurrentViewStateOrNew()
        if(update.skillsListFields.skillsList==skillsListFields.skillsList)
            return

        Log.d(TAG, "SkillsViewModel: setSkillsListFields: ")

        update.skillsListFields = skillsListFields
        setViewState(update)
    }

    fun setCurrentSelectedItemPosition(newPos: Int){
        val update = getCurrentViewStateOrNew()
        if(update.currentSelectedItemPosition==newPos)
            return

        Log.d(TAG, "SkillsViewModel: setSkillsListFields: ")

        update.currentSelectedItemPosition = newPos
        setViewState(update)
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

    fun cancelActiveJobs(){
        mainRepository.cancelActiveJobs()
        handlePendingData() // hide progress bar
    }

    fun handlePendingData(){
        Log.d(TAG, "SkillsViewModel: handlePendingData: ")
        setStateEvent(SkillsStateEvent.None())
    }
}


















