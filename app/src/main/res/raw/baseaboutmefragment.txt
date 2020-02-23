package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.whoiskhamidjon.di.Injectable
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainDataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.util.DataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainDependencyProvider
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel.AboutMeViewModel
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.ABOUT_ME_VIEW_STATE_BUNDLE_KEY

abstract class BaseAboutMeFragment : Fragment(), Injectable {
    private val TAG = "AppDebug"

    lateinit var dependencyProvider: MainDependencyProvider

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var mainStateChangeListener: MainDataStateChangeListener

    lateinit var viewModel: AboutMeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(
                this, dependencyProvider.getVMProviderFactory()
            ).get(AboutMeViewModel::class.java)
        }?: throw Exception("Invalid activity")

        if(savedInstanceState!=null){
            (savedInstanceState[ABOUT_ME_VIEW_STATE_BUNDLE_KEY] as AboutMeViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }

            Log.d(TAG, "BaseAboutMeFragment: onCreate: savedInstance!=null")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    /*
          @fragmentId is id of fragment from graph to be EXCLUDED from action back bar nav
        */
    fun isViewModelInitialized() = ::viewModel.isInitialized

    override fun onSaveInstanceState(outState: Bundle) {

        Log.d(TAG, "BaseAboutMeFragment: onSaveInstanceState: called")

        if(isViewModelInitialized()){
            outState.putParcelable(
                ABOUT_ME_VIEW_STATE_BUNDLE_KEY,
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
            Log.d(TAG, "BaseAboutMeFragment: onAttach: $context must implement DependencyProvider")
        }

        try{
            stateChangeListener = context as DataStateChangeListener
        }catch (e: java.lang.ClassCastException){
            Log.d(
                TAG,
                "BaseAboutMeFragment: onAttach: $context must implement DataStateChangeListener"
            )
        }

        try{
            mainStateChangeListener = context as MainDataStateChangeListener
        }catch (e: java.lang.ClassCastException){
            Log.d(
                TAG,
                "BaseAboutMeFragment: onAttach: $context must implement DataStateChangeListener"
            )
        }
    }
}

















