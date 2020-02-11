package com.hamidjonhamidov.whoiskhamidjon.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.hamidjonhamidov.whoiskhamidjon.models.database.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.requests.NetworkBoundResource
import com.hamidjonhamidov.whoiskhamidjon.requests.firebase.RequestFromFirebase
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.AboutMeDao
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.MyResponse
import com.hamidjonhamidov.whoiskhamidjon.ui.ResponseType
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.util.GenericNetworkResponse
import com.hamidjonhamidov.whoiskhamidjon.util.JobManager
import com.hamidjonhamidov.whoiskhamidjon.util.NetworkSuccessResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AboutMeRepository
@Inject
constructor(
    private val requestFromFirebase: RequestFromFirebase,
    private val aboutMeDao: AboutMeDao,
    private val sessionManager: SessionManager
) : JobManager("AboutMeRepository") {
    private val TAG = "AppDebug"


    fun getAboutMeInfo(): LiveData<DataState<AboutMeViewState>>{
        Log.d(TAG, "AboutMeRepository: getAboutMeInfo: called")

        return object : NetworkBoundResource<AboutMeModel, AboutMeModel, AboutMeViewState>(
            sessionManager.isConnectedToTheInternet(),
            true,
            false,
            true
        ){
            override suspend fun createCacheRequestAndReturn(message: String?) {
                Log.d(TAG, "AboutMeRepository: createCacheRequestAndReturn: ")

                withContext(Main){
                    // finishing by viewing db cache
                    result.addSource(loadFromCache()){viewState: AboutMeViewState? ->

                        onCompleteJob(DataState.data(viewState, message?.let { MyResponse(
                            message,
                            ResponseType.Snackbar()
                        )}))
                    }
                }
            }

            override suspend fun handleNetworkSuccessResponse(response: NetworkSuccessResponse<AboutMeModel>) {
                Log.d(TAG, "AboutMeRepository: handleNetworkSuccessResponse: ${response.body.email}")


                updateLocalDb(response.body)

                createCacheRequestAndReturn(response.successResponseMessage)
            }

            override fun createCall(): LiveData<GenericNetworkResponse<AboutMeModel>> {
                Log.d(TAG, "AboutMeRepository: createCall: ")

                return requestFromFirebase.requestAboutMeInfo()
            }

            override fun loadFromCache(): LiveData<AboutMeViewState> {
                Log.d(TAG, "AboutMeRepository: loadFromCache: ")

                return aboutMeDao.getAboutMeModel()
                    .switchMap {
                        object : LiveData<AboutMeViewState>(){
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

                if(cacheObject==null) return
                Log.d(TAG, "AboutMeRepository: updateLocalDb: trying to insert")

                withContext(IO){
                    launch {
                        Log.d(TAG, "AboutMeRepository: updateLocalDb: almost inserted")
                        aboutMeDao.insertOrReplace(cacheObject)
                    }
                }
            }

            override fun setJob(job: Job) {
                addJob("getAboutMeInfo", job)
            }
        }.asLiveData()
    }
}






















