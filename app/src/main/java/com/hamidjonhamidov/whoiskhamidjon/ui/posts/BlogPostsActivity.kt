package com.hamidjonhamidov.whoiskhamidjon.ui.posts

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.RequestManager
import com.codingwithmitch.openapi.util.BottomNavController
import com.codingwithmitch.openapi.util.BottomNavController.*
import com.codingwithmitch.openapi.util.setUpNavigation
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.BaseActivity
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.BlogPostsConstants.BOTTOM_NAV_BACKSTACK_BUNDLE_KEY
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts.BasePostsFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts.BlogPostsFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts.DeletePostFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.posts.fragments_for_posts.UpdatePostFragment
import com.hamidjonhamidov.whoiskhamidjon.util.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_blog_posts.*
import kotlinx.android.synthetic.main.fragment_blog_posts.*
import javax.inject.Inject

class BlogPostsActivity :
    BaseActivity(),
    PostsDependencyProvider,
    PostsDataStateChangeListener,
    NavGraphProvider,
    OnNavigationGraphChanged
{

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var requestManager: RequestManager

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.blog_posts_nav_host_fragment,
            R.id.nav_blog,
            this,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_posts)

        setUpActionBar()
        setUpBottomNavigationView(savedInstanceState)
    }

    private fun setUpBottomNavigationView(savedInstanceState: Bundle?) {
        bottom_navigation_view.setUpNavigation(bottomNavController)

        if (savedInstanceState == null) {
            bottomNavController.setupBottomNavigationBackStack(null)
            bottomNavController.onNavigationItemSelected()
        } else {
            (savedInstanceState[BOTTOM_NAV_BACKSTACK_BUNDLE_KEY] as IntArray?)?.let { items ->
                val backStack = BackStack()
                backStack.addAll(items.toTypedArray())
                bottomNavController.setupBottomNavigationBackStack(backStack)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save backstack for bottom nav
        outState.putIntArray(
            BOTTOM_NAV_BACKSTACK_BUNDLE_KEY,
            bottomNavController.navigationBackStack.toIntArray()
        )
    }

    private fun setUpActionBar() {
        setSupportActionBar(tool_bar)
    }


    override fun onBackPressed() = bottomNavController.onBackPressed()

    override fun getVMProviderFactory() = providerFactory

    override fun getGlideRequestManager() = requestManager

    override fun getNavGraphId(itemId: Int) =
        when (itemId) {
            R.id.nav_blog -> {
                R.navigation.get_blogs_nav_graph
            }

            R.id.nav_update_blog -> {
                R.navigation.update_blog_nav_graph
            }

            R.id.nav_delete_blog -> {
                R.navigation.delete_blog_nav_graph
            }

            else -> R.navigation.get_blogs_nav_graph
        }

    override fun onGraphChange() {
        cancelActiveJobs()
        expandAppBar()
    }

    fun cancelActiveJobs() {
        val fragments = bottomNavController.fragmentManager
            .findFragmentById(bottomNavController.containerId)
            ?.childFragmentManager
            ?.fragments

        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment is BasePostsFragment)
                    fragment.cancelActiveJobs()
            }
        }

        displayProgressBar(false)
    }

    override fun getParentViewForSnackbar() = root_blog_post_activity

    override fun displayProgressBar(shouldShowProgressBar: Boolean) {
        progress_bar.visibility = if(shouldShowProgressBar) View.VISIBLE else View.GONE
    }

    override fun expandAppBar() = app_bar.setExpanded(true)
}



























