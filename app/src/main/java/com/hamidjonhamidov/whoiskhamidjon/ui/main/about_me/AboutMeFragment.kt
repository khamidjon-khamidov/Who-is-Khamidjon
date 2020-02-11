package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.models.database.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.ui.displaySnackbar
import com.hamidjonhamidov.whoiskhamidjon.ui.main.DateUtil
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent.*
import com.hamidjonhamidov.whoiskhamidjon.util.Constants
import com.hamidjonhamidov.whoiskhamidjon.util.OnSnackbarClicked
import com.hamidjonhamidov.whoiskhamidjon.util.setLeftDrawerListeners
import kotlinx.android.synthetic.main.fragment_about_me.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class AboutMeFragment : BaseAboutMeFragment() {

    private val TAG = "AppDebug"


    lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    lateinit var job: CompletableJob


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
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.setTitle("About Me")
        }
        Log.d(TAG, "AboutMeFragment: onViewCreated: ")

        setLeftDrawerListeners()
        initializeBottomsheet()
        initializeExperiencePeriod()




        if (viewModel.viewState.value?.aboutMeFields?.aboutMeModel == null) {
            subscribeObservers()
            viewModel.setStateEvent(GetAboutMeStateEvent())
        } else {
            updateView(viewModel.viewState.value!!.aboutMeFields.aboutMeModel!!)
        }


        stateChangeListener.shouldStartShimmerInFragment(true)
    }

    private fun initializeBottomsheet() {
        // this is for bottomSheet
        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_about_me)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        tv_education_info_about_me_tuit.setOnClickListener {
            viewModel.viewState.value?.aboutMeFields?.aboutMeModel?.let {
                tv_uni_name_about_me_btm_sheet.setText(Constants.TUIT)

                // bind subject scores and subjects
                val subject = it.convertStrToSubject(it.tuit)
                tv_btm_score_list_about_me.setText(subject.score)
                tv_btm_subject_list_about_me.setText(subject.subject)

                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } ?: let {
                activity?.displaySnackbar(
                    id_about_me_fragment,
                    Constants.NO_DATA_AVAILABLE,
                    "Retry",
                    object : OnSnackbarClicked {
                        override fun onSnackbarClick(snackbar: Snackbar) {
                            viewModel.setStateEvent(GetAboutMeStateEvent())
                        }
                    })
            }
        }

        tv_education_info_about_me_lsbu.setOnClickListener {
            viewModel.viewState.value?.aboutMeFields?.aboutMeModel?.let {
                tv_uni_name_about_me_btm_sheet.setText(Constants.LSBU)

                // bind subject scores and subjects
                val subject = it.convertStrToSubject(it.south_bank)
                tv_btm_score_list_about_me.setText(subject.score)
                tv_btm_subject_list_about_me.setText(subject.subject)

                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } ?: let {
                activity?.displaySnackbar(
                    id_about_me_fragment,
                    Constants.NO_DATA_AVAILABLE,
                    "Retry",
                    object : OnSnackbarClicked {
                        override fun onSnackbarClick(snackbar: Snackbar) {
                            viewModel.setStateEvent(GetAboutMeStateEvent())
                        }
                    })
            }

        }
    }

    private fun subscribeObservers() {

        Log.d(TAG, "AboutMeFragment: subscribeObservers: 1")

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // DataState<AboutMeViewState>

            Log.d(TAG, "AboutMeFragment: subscribeObservers: 2")

            if (dataState != null) {

                dataState.data?.dataReceived?.getContentIfNotHandled()?.aboutMeFields?.let {
                    Log.d(
                        TAG,
                        "AboutMeFragment: subscribeObservers: aboutMeFields:vo ${it.aboutMeModel}"
                    )
                    viewModel.setAboutMeFields(it)
                    stateChangeListener.onDataStateChange(dataState)
                }
            }
        })


        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            if (viewState != null) {

                Log.d(TAG, "AboutMeFragment: subscribeObservers: 3")

                viewState.aboutMeFields.aboutMeModel?.let { aboutMeModel ->
                    updateView(aboutMeModel)
                }
            }

        })
    }

    private fun updateView(aboutMeModel: AboutMeModel) {

        dependencyProvider.getGlideRequestManager().load(aboutMeModel.profile_image_url)
            .into(iv_profile_picture_about_me)

        tv_address_info_about_me.setText(aboutMeModel.address)
        tv_phone_info_about_me.setText(aboutMeModel.phone_number)
        tv_email_info_about_me.setText(aboutMeModel.email)
    }

    private fun initializeExperiencePeriod() {
        job = Job()
        val coroutineScope = CoroutineScope(IO + job)
        coroutineScope.launch {
            while (true) {
                withContext(Main) {
                    tv_pr_ex_info_about_me?.setText(DateUtil.getDifferenceWithCurrentDate())
                }
                delay(1000)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        job.cancel()

        // todo
//        viewModel.setAboutMeFields(
//            AboutMeFields(
//                AboutMeModel(
//                    
//                )
//            )
//        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "AboutMeFragment: onDestroy: ")
    }
}
























