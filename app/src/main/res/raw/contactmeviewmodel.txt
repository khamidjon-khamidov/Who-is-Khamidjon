package com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.viewmodel

import androidx.lifecycle.LiveData
import com.hamidjonhamidov.whoiskhamidjon.ui.BaseViewModel
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.state.ContactMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.state.ContactMeViewState.*
import com.hamidjonhamidov.whoiskhamidjon.util.AbsentLiveData
import javax.inject.Inject

class ContactMeViewModel
@Inject constructor() : BaseViewModel<Any, ContactMeViewState>() {

    override fun handleStateEvent(stateEvent: Any): LiveData<DataState<ContactMeViewState>> {
        // ignore
        return AbsentLiveData.create()
    }

    override fun initNewViewState(): ContactMeViewState {
        return ContactMeViewState()
    }

    fun setContactType(type: Int){
        val update = getCurrentViewStateOrNew()
        if(update.contactType == type) return

        update.contactType = type
        setViewState(update)
    }

    fun setSendMessageFields(sendMessageFields: SendMessageFields) {
        val update = getCurrentViewStateOrNew()
        if (update.sendMessageFields.message == sendMessageFields.message) return

        update.sendMessageFields = sendMessageFields
        setViewState(update)
    }

    fun setSendEmailFields(sendEmailFields: SendEmailFields) {
        val update = getCurrentViewStateOrNew()
        if (update.sendEmailFields.message == sendEmailFields.message) return

        update.sendEmailFields = sendEmailFields
        setViewState(update)
    }

}