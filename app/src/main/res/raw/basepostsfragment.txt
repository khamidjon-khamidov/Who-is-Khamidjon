package com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.di.Injectable
import com.hamidjonhamidov.whoiskhamidjon.ui.DialogButtonClickListener
import com.hamidjonhamidov.whoiskhamidjon.ui.displayDialog
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.BlogPostsConstants.BLOGPOSTS_LIST_VIEW_STATE_BUNDLE_KEY
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.PostsDataStateChangeListener
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.PostsDependencyProvider
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.state.BlogsPostsViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.viewmodel.BlogPostsViewModel
import com.hamidjonhamidov.whoiskhamidjon.util.DataStateChangeListener
import java.lang.ClassCastException

abstract class BasePostsFragment : Fragment(), Injectable {

    private val TAG = "AppDebug"


    lateinit var dependencyProvider: PostsDependencyProvider

    lateinit var stateChangeListener: DataStateChangeListener

    lateinit var postsStateChangeListener: PostsDataStateChangeListener

    lateinit var viewModel: BlogPostsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(
                this, dependencyProvider.getVMProviderFactory()
            ).get(BlogPostsViewModel::class.java)
        } ?: throw Exception("Invalid activity")


        if (savedInstanceState != null) {
            (savedInstanceState[BLOGPOSTS_LIST_VIEW_STATE_BUNDLE_KEY]
                    as BlogsPostsViewState?)?.let { viewState ->
                viewModel.setViewState(viewState)
            }

            Log.d(TAG, "BasePostsFragment: onCreate: savedinstance!=null")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    fun isViewModelInitialized() = ::viewModel.isInitialized

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "BasePostsFragment: onSaveInstanceState: called")

        if (isViewModelInitialized()) {
            outState.putParcelable(
                BLOGPOSTS_LIST_VIEW_STATE_BUNDLE_KEY,
                viewModel.viewState.value
            )
        }
        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            dependencyProvider = context as PostsDependencyProvider
        } catch (e: ClassCastException) {
            Log.d(TAG, "BasePostsFragment: onAttach: $context must implement dependecy provider")
        }

        try {
            stateChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Log.d(TAG, "BasePostsFragment: onAttach: $context must implement dependecy provider")
        }

        try {
            postsStateChangeListener = context as PostsDataStateChangeListener
        } catch (e: ClassCastException) {
            Log.d(TAG, "BasePostsFragment: onAttach: $context must implement dependecy provider")
        }
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
                    "It should be noted that this API has been taken from https://jsonplaceholder.typicode.com/ website. Unfortunately, update and delete commands are fake in the site as well. Although you take success response, it is not updated in the site itself.",
                    false,
                    object : DialogButtonClickListener {
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


    fun cancelActiveJobs(){
        viewModel.cancelActiveJobs()
    }
}


































