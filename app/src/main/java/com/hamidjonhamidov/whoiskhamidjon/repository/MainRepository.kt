package com.hamidjonhamidov.whoiskhamidjon.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel
import com.hamidjonhamidov.whoiskhamidjon.requests.NetworkBoundResource
import com.hamidjonhamidov.whoiskhamidjon.requests.firebase.RequestFromFirebase
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.MainDao
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.MyResponse
import com.hamidjonhamidov.whoiskhamidjon.ui.ResponseType
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsViewState.SkillsListFields
import com.hamidjonhamidov.whoiskhamidjon.util.GenericNetworkResponse
import com.hamidjonhamidov.whoiskhamidjon.util.JobManager
import com.hamidjonhamidov.whoiskhamidjon.util.NetworkSuccessResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val requestFromFirebase: RequestFromFirebase,
    private val mainDao: MainDao,
    private val sessionManager: SessionManager
) : JobManager("AboutMeRepository") {
    private val TAG = "AppDebug"


    fun getAboutMeInfo(): LiveData<DataState<AboutMeViewState>> {
        Log.d(TAG, "AboutMeRepository: getAboutMeInfo: called")

        return object : NetworkBoundResource<AboutMeModel, AboutMeModel, AboutMeViewState>(
            sessionManager.isConnectedToTheInternet(),
            true,
            false,
            true
        ) {
            override suspend fun createCacheRequestAndReturn(message: String?) {
                Log.d(TAG, "AboutMeRepository: createCacheRequestAndReturn: ")

                withContext(Main) {
                    // finishing by viewing db cache
                    result.addSource(loadFromCache()) { viewState: AboutMeViewState? ->

                        onCompleteJob(DataState.data(viewState, message?.let {
                            MyResponse(
                                message,
                                ResponseType.Snackbar()
                            )
                        }))
                    }
                }
            }

            override suspend fun handleNetworkSuccessResponse(response: NetworkSuccessResponse<AboutMeModel>) {
                Log.d(
                    TAG,
                    "AboutMeRepository: handleNetworkSuccessResponse: ${response.body.email}"
                )


                updateLocalDb(response.body)

                createCacheRequestAndReturn(response.successResponseMessage)
            }

            override fun createCall(): LiveData<GenericNetworkResponse<AboutMeModel>> {
                Log.d(TAG, "AboutMeRepository: createCall: ")

                return requestFromFirebase.requestAboutMeInfo()
            }

            override fun loadFromCache(): LiveData<AboutMeViewState> {
                Log.d(TAG, "AboutMeRepository: loadFromCache: ")

                return mainDao.getAboutMeModel()
                    .switchMap {
                        object : LiveData<AboutMeViewState>() {
                            override fun onActive() {
                                super.onActive()
                                value = AboutMeViewState(
                                    AboutMeViewState.AboutMeFields(
                                        aboutMeModel = it
                                    )
                                )
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: AboutMeModel?) {
                Log.d(TAG, "AboutMeRepository: updateLocalDb: ")

                if (cacheObject == null) return
                Log.d(TAG, "AboutMeRepository: updateLocalDb: trying to insert")

                withContext(IO) {
                    launch {
                        Log.d(TAG, "AboutMeRepository: updateLocalDb: almost inserted")
                        mainDao.insertOrReplaceAboutMe(cacheObject)
                    }
                }
            }

            override fun setJob(job: Job) {
                addJob("getAboutMeInfo", job)
            }
        }.asLiveData()
    }

    fun getSkillsList(): LiveData<DataState<SkillsViewState>> {
        Log.d(TAG, "MainRepository: getSkillsList: called")

        return object : NetworkBoundResource<List<SkillModel>, List<SkillModel>, SkillsViewState>(
            sessionManager.isConnectedToTheInternet(),
            true,
            false,
            true
        ) {
            override suspend fun createCacheRequestAndReturn(message: String?) {
                Log.d(TAG, "MainRepository: createCacheRequestAndReturn: ")

                withContext(Main) {
                    // finishing by viewing db cache
                    result.addSource(loadFromCache()) { viewState: SkillsViewState? ->
                        onCompleteJob(DataState.data(viewState, message?.let {
                            MyResponse(
                                message,
                                ResponseType.Snackbar()
                            )
                        }))
                    }
                }
            }

            override suspend fun handleNetworkSuccessResponse(response: NetworkSuccessResponse<List<SkillModel>>) {
                Log.d(TAG, "MainRepository: handleNetworkSuccessResponse: ")

                updateLocalDb(response.body)

                createCacheRequestAndReturn(response.successResponseMessage)
            }

            override fun createCall(): LiveData<GenericNetworkResponse<List<SkillModel>>> {
                Log.d(TAG, "MainRepository: createCall: ")

                return requestFromFirebase.requestSkills()
            }

            override fun loadFromCache(): LiveData<SkillsViewState> {
                Log.d(TAG, "MainRepository: loadFromCache: ")

                return mainDao.getSkills()
                    .switchMap {
                        object : LiveData<SkillsViewState>() {
                            override fun onActive() {
                                super.onActive()

                                value = SkillsViewState(
                                    SkillsListFields(
                                        skillsList = it
                                    )
                                )
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: List<SkillModel>?) {
                Log.d(TAG, "MainRepository: updateLocalDb: ")

                if (cacheObject == null) return

                withContext(IO) {
                    launch {
                        Log.d(TAG, "MainRepository: updateLocalDb: ")
                        for (skill in cacheObject) {
                            Log.d(TAG, "MainRepository: updateLocalDb: ${skill.id}")
                            mainDao.insertOrReplaceSkills(skill)
                        }
                    }
                }
            }

            override fun setJob(job: Job) {
                addJob("getSkillsList", job)
            }
        }.asLiveData()
    }
}






















