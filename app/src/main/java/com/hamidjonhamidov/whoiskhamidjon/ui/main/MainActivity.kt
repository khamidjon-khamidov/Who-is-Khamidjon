package com.hamidjonhamidov.whoiskhamidjon.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.RequestManager
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.BaseActivity
import com.hamidjonhamidov.whoiskhamidjon.util.MainNavigation
import com.hamidjonhamidov.whoiskhamidjon.util.ViewModelProviderFactory
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_left_drawer.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
    MainDependencyProvider {

    lateinit var toolbarForDrawer: Toolbar
    lateinit var toolbarForNavBack: Toolbar

    var locked = false

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var requestManager: RequestManager

    lateinit var slidingRootNav: SlidingRootNav

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setting up toolbar
        toolbarForDrawer = findViewById(R.id.my_toolbar)
        toolbarForNavBack = findViewById(R.id.my_toolbar2)

        setSupportActionBar(toolbarForDrawer)

        // this is navigation for main
        setUpNavigation(savedInstanceState, R.id.menu_item_about_me)
    }

    override fun shouldStartShimmerInFragment(shouldAnimate: Boolean) {
        if(shouldAnimate){
            shimmer_full_screen_container.visibility = View.VISIBLE
            shimmer_full_screen_container?.startShimmer()
        }
        else {
            shimmer_full_screen_container.visibility = View.GONE
            shimmer_full_screen_container?.stopShimmer()
        }
    }

    override fun lockDrawer(isLocked: Boolean, menuId: Int) {

        if(locked == isLocked) return

        locked = isLocked

        if(isLocked){
            toolbarForDrawer.visibility = View.GONE
            toolbarForNavBack.visibility = View.VISIBLE

            slidingRootNav.isMenuLocked = true
            setSupportActionBar(toolbarForNavBack)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            toolbarForDrawer.visibility = View.VISIBLE
            toolbarForNavBack.visibility = View.GONE

            setSupportActionBar(toolbarForDrawer)
            setUpNavigation(null, menuId)
        }

    }

    override fun closeLeftNavigationMenu() {
        slidingRootNav?.closeMenu()
    }

    private fun setUpNavigation(savedInstanceState: Bundle?, id: Int) {
        slidingRootNav = SlidingRootNavBuilder(this)
            .withToolbarMenuToggle(toolbarForDrawer)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.menu_left_drawer)
            .inject()

        MainNavigation.setSelected(
            this,
            id
        )
        setListeners()


    }

    private fun setListeners() {

        menu_item_source_code.setOnClickListener {
            slidingRootNav.closeMenu()
            // todo
        }

        menu_item_personal_posts.setOnClickListener{
            slidingRootNav.closeMenu()
            // todo
        }

        menu_item_exit.setOnClickListener {
            finish()
        }

    }

    override fun getVMProviderFactory(): ViewModelProviderFactory {
        return providerFactory
    }

    override fun getGlideRequestManager(): RequestManager {
        return requestManager
    }
}



















