package com.hamidjonhamidov.whoiskhamidjon.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import com.hamidjonhamidov.whoiskhamidjon.requests.NetworkBoundResource
import com.hamidjonhamidov.whoiskhamidjon.requests.api.ApiBlogPostsService
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.BlogPostsDao
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.MyResponse
import com.hamidjonhamidov.whoiskhamidjon.ui.ResponseType
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.state.BlogsPostsViewState
import com.hamidjonhamidov.whoiskhamidjon.util.GenericNetworkResponse
import com.hamidjonhamidov.whoiskhamidjon.util.JobManager
import com.hamidjonhamidov.whoiskhamidjon.util.NetworkSuccessResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class BlogRepository
@Inject
constructor(
    val apiBlogPostsService: ApiBlogPostsService,
    val sessionManager: SessionManager,
    val blogPostsDao: BlogPostsDao
) : JobManager("BlogRepository") {

    private val TAG = "AppDebug"

    fun getAllBlogPosts(): LiveData<DataState<BlogsPostsViewState>> {

        Log.d(TAG, "BlogRepository: getAllBlogPosts: ${sessionManager.isConnectedToTheInternet()}")

        return object :
            NetworkBoundResource<List<BlogPostModel>, List<BlogPostModel>, BlogsPostsViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                false,
                true
            ) {
            override suspend fun createCacheRequestAndReturn(message: String?) {

                withContext(Main) {

                    result.addSource(loadFromCache()) { viewState: BlogsPostsViewState? ->
                        onCompleteJob(DataState.data(viewState, message?.let {
                            MyResponse(
                                message,
                                ResponseType.Snackbar()
                            )
                        }))
                    }
                }
            }

            override suspend fun handleNetworkSuccessResponse(response: NetworkSuccessResponse<List<BlogPostModel>>) {
                Log.d(TAG, "BlogRepository: handleNetworkSuccessResponse: ")

                updateLocalDb(response.body)

                createCacheRequestAndReturn(response.successResponseMessage)
            }

            override fun createCall(): LiveData<GenericNetworkResponse<List<BlogPostModel>>> {
                Log.d(TAG, "BlogRepository: createCall: ")

                return apiBlogPostsService.getPosts()
            }

            override fun loadFromCache(): LiveData<BlogsPostsViewState> {
                Log.d(TAG, "BlogRepository: loadFromCache: ")

                return blogPostsDao.getBlogPosts()
                    .switchMap {
                        object : LiveData<BlogsPostsViewState>() {
                            override fun onActive() {
                                super.onActive()
                                value = BlogsPostsViewState(
                                    BlogsPostsViewState.BlogPostsFields(
                                        blogPostsList = it
                                    )
                                )
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: List<BlogPostModel>?) {
                Log.d(TAG, "BlogRepository: updateLocalDb: ")

                if (cacheObject == null) return

                withContext(IO) {
                    launch {
                        Log.d(TAG, "BlogRepository: updateLocalDb: ")
                        for (blog in cacheObject) {
                            blogPostsDao.insertOrReplaceBlogPost(blog)
                        }
                    }
                }
            }

            override fun setJob(job: Job) {
                addJob("getAllBlogPosts", job)
            }
        }.asLiveData()
    }

    fun postBlogPost(userId: Int, title: String, body: String): LiveData<DataState<BlogsPostsViewState>>{
        return object : NetworkBoundResource<BlogPostModel, BlogPostModel, BlogsPostsViewState>(
            sessionManager.isConnectedToTheInternet(),
            true,
            false,
            true
        ){
            override suspend fun createCacheRequestAndReturn(message: String?) {
                withContext(Main){
                    result.addSource(loadFromCache()){ viewState: BlogsPostsViewState? ->

                        onCompleteJob(DataState.data(viewState,
                            MyResponse(
                                "Successfully posted, but it will not be updated on website!",
                                ResponseType.Snackbar()
                            )
                        ))
                    }
                }
            }

            override suspend fun handleNetworkSuccessResponse(response: NetworkSuccessResponse<BlogPostModel>) {

                Log.d(
                    TAG,
                    "BlogRepository: handleNetworkSuccessResponse: succesRes: ${response.body}"
                )
                updateLocalDb(response.body)

                createCacheRequestAndReturn(response.successResponseMessage)
            }

            override fun createCall(): LiveData<GenericNetworkResponse<BlogPostModel>> {
                Log.d(TAG, "BlogRepository: createCall: newPost: called")
                return apiBlogPostsService.postBlogPost(
                    title,
                    body,
                    userId)
            }

            override fun loadFromCache(): LiveData<BlogsPostsViewState> {
                return blogPostsDao.getBlogPosts()
                    .switchMap {
                        object : LiveData<BlogsPostsViewState>(){
                            override fun onActive() {
                                super.onActive()
                                value = BlogsPostsViewState(
                                    BlogsPostsViewState.BlogPostsFields(
                                        blogPostsList = it
                                    )
                                )
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: BlogPostModel?) {
                if(cacheObject==null) return

                withContext(IO){
                    launch {
                        blogPostsDao.insertOrReplaceBlogPost(
                            BlogPostModel(
                                cacheObject.id,
                                userId,
                                title,
                                body
                            )
                        )
                    }
                }
            }

            override fun setJob(job: Job) {

            }
        }.asLiveData()
    }
}

















