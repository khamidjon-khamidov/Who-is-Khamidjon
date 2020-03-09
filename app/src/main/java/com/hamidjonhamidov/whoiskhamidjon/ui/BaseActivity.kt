package com.hamidjonhamidov.whoiskhamidjon.ui

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import com.hamidjonhamidov.whoiskhamidjon.util.DataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.util.OnSnackbarClicked
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity(),
    DataStateChangeListener
{
    private val TAG = "AppDebug"

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onDataStateChange(dataState: DataState<*>?) {
        Log.d(TAG, "BaseActivity: onDataStateChange: called")
        dataState?.let {
            GlobalScope.launch(Main) {

                displayProgressBar(it.loading.isLoading)

                it.error?.let { errorEvent->
                    handleStateError(errorEvent)
                }

                it.data?.let {

                    it.responseReceived?.let {  responseReceived->
                        Log.d(TAG, "BaseActivity: onDataStateChange: responseReceived")
                        handleStateResponse(responseReceived)
                    }
                }
            }
        }
    }

    abstract fun getParentViewForSnackbar(): View

    abstract fun displayProgressBar(shouldShowProgressBar: Boolean)

    private fun handleStateResponse(event: Event<MyResponse>){

        Log.d(TAG, "BaseActivity: handleStateResponse: getContentIfNotHandled := ${event.peekContent()}")
        event.getContentIfNotHandled()?.let{

            when(it.responseType){
                is ResponseType.Snackbar ->{
                    it.message?.let{ message ->
                        displaySnackbar(getParentViewForSnackbar(), message, "OK", object : OnSnackbarClicked{
                            override fun onSnackbarClick(snackbar: Snackbar) {

                            }
                        })
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
//                        displaySnackbar(message)
                        displayErrorDialog(message)
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