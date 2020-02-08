package com.hamidjonhamidov.whoiskhamidjon.ui

import android.util.Log
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity(), DataStateChangeListener
{
    private val TAG = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            GlobalScope.launch(Main) {

                it.error?.let { errorEvent->
                    handleStateError(errorEvent)
                }

                it.data?.let {
                    it.responseReceived?.let {  responseReceived->
                        handleStateResponse(responseReceived)
                    }
                }
            }
        }
    }

    private fun handleStateResponse(event: Event<MyResponse>){
        event.getContentIfNotHandled()?.let{

            when(it.responseType){
                is ResponseType.Snackbar ->{
                    it.message?.let{message ->
                        displaySnackbar(message)
                    }
                }

                is ResponseType.Dialog ->{
                    it.message?.let{ message ->
                        displaySuccessDialog(message)
                    }
                }

                is ResponseType.None -> {
                    Log.i(TAG, "handleStateResponse: ${it.message}")
                }
            }

        }
    }

    private fun handleStateError(event: Event<StateError>){
        event.getContentIfNotHandled()?.let{


            when(it.myResponse.responseType){
                is ResponseType.Snackbar ->{
                    it.myResponse.message?.let{ message ->
                        displaySnackbar(message)
                    }
                }

                is ResponseType.Dialog ->{
                    it.myResponse.message?.let{ message ->
                        displayErrorDialog(message)
                    }
                }

                is ResponseType.None -> {
                    Log.i(TAG, "handleStateError: ${it.myResponse.message}")
                }
            }
        }
    }
}