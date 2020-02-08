package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.whoiskhamidjon.di.Injectable
import com.hamidjonhamidov.whoiskhamidjon.ui.DataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainDependencyProvider
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel.AboutMeViewModel
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.ABOUT_ME_VIEW_STATE_BUNDLE_KEY

abstract class BaseAboutMeFragment : Fragment(), Injectable {
    private val TAG = "AppDebug"

    lateinit var dependencyProvider: MainDependencyProvider

    lateinit var stateChangeListener: DataStateChangeListener

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
        }
    }


    fun isViewModelInitialized() = ::viewModel.isInitialized

    override fun onSaveInstanceState(outState: Bundle) {

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
    }
}

















