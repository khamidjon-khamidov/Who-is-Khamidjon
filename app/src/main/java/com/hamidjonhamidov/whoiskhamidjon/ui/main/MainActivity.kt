package com.hamidjonhamidov.whoiskhamidjon.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.google.firebase.firestore.*
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.models.database.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.ui.BaseActivity
import com.hamidjonhamidov.whoiskhamidjon.util.Constants.menuItems
import com.hamidjonhamidov.whoiskhamidjon.util.MainNavigation
import com.hamidjonhamidov.whoiskhamidjon.util.ViewModelProviderFactory
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_left_drawer.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
    MainDependencyProvider {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory


    lateinit var slidingRootNav: SlidingRootNav


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
        // setting up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // this is navigation for main
        setUpNavigation(savedInstanceState)
//        }


    }

    private fun setUpNavigation(savedInstanceState: Bundle?) {
        slidingRootNav = SlidingRootNavBuilder(this)
            .withToolbarMenuToggle(toolbar)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.menu_left_drawer)
            .inject()

        MainNavigation.setSelected(
            this,
            R.id.menu_item_about_me
        )
        setListeners()
    }


    private val TAG = "AppDebug"
    private fun setListeners() {

//            mdB = FirebaseFirestore.getInstance()
//            mdB.collection("about_me")
//                .get()
//                .addOnSuccessListener {
//
//                    Log.d("AppDebug", "MainActivity: onEvent: hello")
//                    for (docs in it.documents) {
//                        val aboutMe = docs.toObject(AboutMeModel::class.java)
//                        Log.d("AppDebug", "MainActivity: setListeners: $aboutMe")
//                    }
//                }

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
}



















