package com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.whoiskhamidjon.di.Injectable
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainDataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.util.DataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainDependencyProvider
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.state.ContactMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.viewmodel.ContactMeViewModel
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.CONTACT_ME_VIEW_STATE_BUNDLE_KEY
import java.lang.ClassCastException

abstract class BaseContactMeFragment: Fragment(), Injectable {
    private val TAG = "AppDebug"

    lateinit var dependencyProvider: MainDependencyProvider

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var mainStateChangeListener: MainDataStateChangeListener

    lateinit var viewModel: ContactMeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run{
            ViewModelProvider(
                this, dependencyProvider.getVMProviderFactory()
            ).get(ContactMeViewModel::class.java)
        }?: throw Exception("Invalid activity")

        if(savedInstanceState != null){
            (savedInstanceState[CONTACT_ME_VIEW_STATE_BUNDLE_KEY] as ContactMeViewState)?.let { viewState->
                viewModel.setViewState(viewState)
            }

            Log.d(TAG, "BaseContactMeFragment: onCreate: savedInstance!=null")
        }
    }

    fun isViewModelInitialized() = ::viewModel.isInitialized

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "BaseContactMeFragment: onSaveInstanceState: called")

        if(isViewModelInitialized()){
            outState.putParcelable(
                CONTACT_ME_VIEW_STATE_BUNDLE_KEY,
                viewModel.viewState.value
            )
        }

        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try{
            dependencyProvider = context as MainDependencyProvider
        } catch (e: ClassCastException){
            Log.d(TAG, "BaseContactMeFragment: onAttach: $context must implement")
        }

        try {
            stateChangeListener = context as DataStateChangeListener
        } catch (e : ClassCastException){
            Log.d(TAG, "BaseContactMeFragment: onAttach: $context must implement")
        }

        try {
            mainStateChangeListener = context as MainDataStateChangeListener
        } catch (e : ClassCastException){
            Log.d(TAG, "BaseContactMeFragment: onAttach: $context must implement")
        }
    }

}























