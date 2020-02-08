package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent.*
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState.*
import com.hamidjonhamidov.whoiskhamidjon.ui.setListenersForLeftDrawer
import com.hamidjonhamidov.whoiskhamidjon.util.Constants
import com.hamidjonhamidov.whoiskhamidjon.util.MainNavigation
import com.hamidjonhamidov.whoiskhamidjon.util.setLeftDrawerListeners

class AboutMeFragment : BaseAboutMeFragment() {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "AboutMeFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        subscribeObservers()

        viewModel.setStateEvent(GetAboutMeStateEvent())
    }

    private fun initialize() {
        setLeftDrawerListeners()
    }


    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState -> // DataState<AboutMeViewState>
            if (dataState != null) {
                Log.d(TAG, "AboutMeFragment: subscribeObservers: dataStateChange: dataState: ${dataState}")
                stateChangeListener.onDataStateChange(dataState)
            }
        })


        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {
                Log.d(
                    TAG,
                    "AboutMeFragment: subscribeObservers: viewStateChange: ${viewState.aboutMeFields.aboutMeModel}"
                )
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // todo
//        viewModel.setAboutMeFields(
//            AboutMeFields(
//
//            )
//        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "AboutMeFragment: onDestroy: ")
    }
}
























