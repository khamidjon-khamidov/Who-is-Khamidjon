package com.hamidjonhamidov.whoiskhamidjon.requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.MyResponse
import com.hamidjonhamidov.whoiskhamidjon.ui.ResponseType
import com.hamidjonhamidov.whoiskhamidjon.util.*
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.ERROR_CHECK_NETWORK_CONNECTION
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.ERROR_UNKNOWN
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.NETWORK_TIMEOUT
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.SLOW_NETWORK
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.TESTING_CACHE_DELAY
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.TESTING_NETWORK_DELAY
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.UNABLE_TO_CONNECT_TO_INTERNET
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class NetworkBoundResource<ResponseObject, CacheObject, ViewStateType>(
    isNetworkAvailable: Boolean, // is this network connection
    isNetworkRequest: Boolean, // is this network request
    shouldCancelIfNoInternet: Boolean, // should this job be cancelled if there is no network
    shouldLoadFromCache: Boolean // should the cache data be loaded?
){
    private val TAG = "AppDebug"

    protected val result = MediatorLiveData<DataState<ViewStateType>>()
    protected lateinit var job: CompletableJob
    protected lateinit var coroutineScope: CoroutineScope

    init {
        Log.d(TAG, "NetworkBoundResource: init: called")
        setJob(initNewJob())
        setValue(DataState.loading(isLoading = true, cachedData = null))

        if(shouldLoadFromCache){
            // view cache to start
            val dbSource = loadFromCache()
            result.addSource(dbSource){
                result.removeSource(dbSource)
                setValue(DataState.loading(isLoading = true, cachedData = it))
            }
        }

        if(isNetworkRequest){
            if(isNetworkAvailable){
                doNetworkRequest()
            }
            else {
                if(shouldCancelIfNoInternet){
                    onErrorReturn(
                        UNABLE_TO_CONNECT_TO_INTERNET,
                        shouldUseDialog = true,
                        shouldUseSnackbar = false
                    )
                } else {
                    doCacheRequest(Constants.REFRESHED_FROM_CACHE)
                }
            }
        } else {
            doCacheRequest(Constants.REFRESHED_FROM_CACHE)
        }
    }

    fun doCacheRequest(message: String? = null){
        coroutineScope.launch {
//            delay(TESTING_CACHE_DELAY)

            // view only cache and return
            createCacheRequestAndReturn(message)
        }
    }

    fun doNetworkRequest(){
        coroutineScope.launch {

            // simulate a network delay for testing
            delay(TESTING_NETWORK_DELAY)

            withContext(Main){
                // make a network call
                val internetResponse = createCall()
                result.addSource(internetResponse){ response ->
                    result.removeSource(internetResponse)

                    coroutineScope.launch {
                        handleNetworkCall(response)
                    }
                }
            }
        }

        GlobalScope.launch(IO) {
            delay(NETWORK_TIMEOUT)

            if(!job.isCompleted){
                Log.d(TAG, "NetworkBoundResource: doNetworkRequest: network timeout")
                job.cancel(CancellationException(SLOW_NETWORK))
            }
        }
    }

    suspend fun handleNetworkCall(response: GenericNetworkResponse<ResponseObject>){
        Log.d(TAG, "NetworkBoundResource: handleNetworkCall: $response")
        when(response){
            is NetworkSuccessResponse -> {
                handleNetworkSuccessResponse(response)
            }

            is NetworkErrorResponse -> {
                Log.d(TAG, "NetworkBoundResource: handleNetworkCall: er error=$response.errorMessage")
                onErrorReturn(response.errorMessage, false, true)
            }

            is NetworkEmptyResponse -> {
                Log.d(TAG, "NetworkBoundResource: handleNetworkCall: er empty")
                onErrorReturn("HTTP 204. Returned NOTHING", true, false)
            }
        }

    }

    fun onCompleteJob(dataState: DataState<ViewStateType>){
        Log.d(TAG, "NetworkBoundResource: onCompleteJob: ${dataState.data?.dataReceived?.peekContent()}")

        GlobalScope.launch(Main) {
            job.complete()
            setValue(dataState)
        }
    }

    fun onErrorReturn(errorMessage: String?, shouldUseDialog: Boolean, shouldUseSnackbar: Boolean){
        var msg = errorMessage
        var useDialog = shouldUseDialog
        var responseType: ResponseType = ResponseType.None()

        if(msg == null){
            msg = ERROR_UNKNOWN
        }
        else if (Constants.isNetworkError(msg)){
            msg = ERROR_CHECK_NETWORK_CONNECTION
            useDialog = false
        }

        if(shouldUseSnackbar)
            responseType = ResponseType.Snackbar()

        if(useDialog)
            responseType = ResponseType.Dialog()

        Log.d(TAG, "NetworkBoundResource: onErrorReturn: called")
        onCompleteJob(DataState.error(MyResponse(msg, responseType)))
    }

    fun setValue(dataState: DataState<ViewStateType>){
        result.value = dataState
    }

    @UseExperimental(InternalCoroutinesApi::class)
    private fun initNewJob(): Job{
        job = Job() // create new job
        job.invokeOnCompletion(onCancelling = true, invokeImmediately = true, handler = object: CompletionHandler{
            override fun invoke(cause: Throwable?) {
                if(job.isCancelled){
                    Log.e(TAG, "NetworkBoundResource: Job has been cancelled.")
                    cause?.let{
                        onErrorReturn(it.message, false, true)
                    }?: onErrorReturn("Unknown error.", false, true)
                }
                else if(job.isCompleted){
                    Log.e(TAG, "NetworkBoundResource: Job has been completed.")
                    // Do nothing? Should be handled already
                }
            }
        })
        coroutineScope = CoroutineScope(IO + job)
        return job
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    abstract suspend fun createCacheRequestAndReturn(message: String? = null)

    abstract suspend fun handleNetworkSuccessResponse(response: NetworkSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericNetworkResponse<ResponseObject>>

    abstract fun loadFromCache(): LiveData<ViewStateType>

    abstract suspend fun updateLocalDb(cacheObject: CacheObject?)

    abstract fun setJob(job: Job)
}

















