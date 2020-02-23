package com.hamidjonhamidov.whoiskhamidjon.util

import android.util.Log
import kotlinx.coroutines.Job

open class JobManager(
    protected val className: String
){
    private val TAG = "AppDebug"

    protected val jobs: HashMap<String, Job> = HashMap()

    fun addJob(methodName: String, job: Job){
        cancelJob(methodName)
        jobs[methodName] = job
    }

    fun cancelJob(methodName: String){
        getJob(methodName)?.cancel()
    }

    fun getJob(methodName: String): Job?{
        if(jobs.containsKey(methodName)){
            jobs[methodName]?.let {
                return it
            }
        }
        return null
    }

    fun cancelActiveJobs(){
        for((methodName, job) in jobs){
            if(job.isActive){
                Log.e(TAG, "$className cancelActiveJobs: $methodName")
                job.cancel()
            }
        }
    }
}