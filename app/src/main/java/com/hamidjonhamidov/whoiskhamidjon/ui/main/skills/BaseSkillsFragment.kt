package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.whoiskhamidjon.di.Injectable
import com.hamidjonhamidov.whoiskhamidjon.ui.DataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainDependencyProvider
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel.AboutMeViewModel
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel.AboutMeViewModel_Factory
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.viewmodel.SkillsViewModel
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.SKILLS_LIST_VIEW_STATE_BUNDLE_KEY
import java.lang.Exception

abstract class BaseSkillsFragment : Fragment(), Injectable {
    private val TAG = "AppDebug"

    lateinit var dependencyProvider: MainDependencyProvider

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var viewModel: SkillsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run{
            ViewModelProvider(
                this, dependencyProvider.getVMProviderFactory()
            ).get(SkillsViewModel::class.java)
        }?: throw Exception("Invalid activity")

        if(savedInstanceState != null){
            (savedInstanceState[SKILLS_LIST_VIEW_STATE_BUNDLE_KEY] as SkillsViewState?)?.let {  viewState->
                viewModel.setViewState(viewState)
            }

            Log.d(TAG, "BaseSkillsFragment: onCreate: savedinstance!=null")
        }
    }

    fun isViewModelInitialized() = ::viewModel.isInitialized

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "BaseSkillsFragment: onSaveInstanceState: called")

        if(isViewModelInitialized()){
            outState.putParcelable(
                SKILLS_LIST_VIEW_STATE_BUNDLE_KEY,
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
            Log.d(TAG, "BaseSkillsFragment: onAttach: $context must implement DependencyProvider")
        }

        try{
            stateChangeListener = context as DataStateChangeListener
        }catch (e: java.lang.ClassCastException){
            Log.d(
                TAG,
                "BaseSkillsFragment: onAttach: $context must implement DataStateChangeListener"
            )
        }
    }
}