package com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts


import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.DialogButtonClickListener
import com.hamidjonhamidov.whoiskhamidjon.ui.displayDialog
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.BlogPostsRecyclerAdapter
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.state.BlogPostsStateEvent.GetBlogListStateEvent
import com.hamidjonhamidov.whoiskhamidjon.util.setActionBarTitle
import kotlinx.android.synthetic.main.fragment_blog_posts.*

class BlogPostsFragment : BasePostsFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val TAG = "AppDebug"

    private lateinit var recyclerAdapter: BlogPostsRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "BlogPostsFragment: onViewCreated: ")

        setActionBarTitle("Posts")

        swipe_refresh_fragment_blog_posts.setOnRefreshListener(this)
        initRecyclerView()

        if(viewModel.getBlogPosts() == null){
            subscribeObservers()
            viewModel.setStateEvent(GetBlogListStateEvent())
        } else {
            recyclerAdapter.submitList(viewModel.getBlogPosts()!!)
        }
    }

    fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->

            if(dataState!=null){
                dataState.data?.dataReceived?.getContentIfNotHandled()?.blogPostsFields?.let {
                    viewModel.setBlogListFields(it)
                    Log.d(TAG, "BlogPostsFragment: subscribeObservers: dataStateChange")
                    stateChangeListener.onDataStateChange(dataState)
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState->
            if(viewState != null) {
                viewState.blogPostsFields.blogPostsList?.let {
                    Log.d(TAG, "BlogPostsFragment: subscribeObservers: viewState: $it")
                    recyclerAdapter.submitList(it)
                }
            }
        })
    }


    fun initRecyclerView(){
        rv_blog_posts.apply {

            recyclerAdapter = BlogPostsRecyclerAdapter()
            layoutManager = LinearLayoutManager(this@BlogPostsFragment.context)
            adapter = recyclerAdapter
        }

    }

    override fun onPause() {
        super.onPause()
        postsStateChangeListener.expandAppBar()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "BlogPostsFragment: onDestroy: ")
    }

    override fun onRefresh() {
        viewModel.setStateEvent(GetBlogListStateEvent())
        Log.d(TAG, "BlogPostsFragment: onRefresh: called")
        swipe_refresh_fragment_blog_posts.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.info_menu_posts, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.icon_info_info_menu -> {
                activity?.displayDialog(
                    "OK",
                    "",
                    "This API Source",
                    "It should be noted that this API has been taken from https://jsonplaceholder.typicode.com/ website. Unfortunately, update and delete commands are fake in the site. Although you get success response, it is not updated in the site itself.",
                    false,
                    object : DialogButtonClickListener{
                        override fun onOkClick(dialog: Dialog) {
                            dialog.hide()
                        }

                        override fun onNoClick(dialog: Dialog) {
                            // ignore
                        }
                    })
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}





















