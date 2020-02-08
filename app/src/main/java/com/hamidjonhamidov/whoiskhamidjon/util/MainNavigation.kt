@file:Suppress("DEPRECATION")

package com.hamidjonhamidov.whoiskhamidjon.util

import android.util.Log
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_app.AboutAppFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.AboutMeFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.ContactFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.contact_me.ContactTypeFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.persojal_projects.PersonalProjectsFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.SkillsListFragment
import com.hamidjonhamidov.whoiskhamidjon.ui.setListenersForLeftDrawer

object MainNavigation {

    fun setSelected(activity: MainActivity, selectedItem: Int) {
        val menuItems = Constants.menuItems

        for (item in menuItems) {
            val cardView = activity.findViewById<CardView>(item)

            if (item == selectedItem) {
                cardView.setCardBackgroundColor(activity.resources.getColor(R.color.green))
            } else {
                cardView.setCardBackgroundColor(activity.resources.getColor(R.color.colorPrimary))
            }

            activity.slidingRootNav.closeMenu()
        }


    }
}

fun Fragment.setLeftDrawerListeners() {
    val TAG = "AppDebug"
    activity?.let { it as MainActivity

        it.findViewById<View>(R.id.menu_item_about_me)?.let { view ->
            view.setOnClickListener { _ ->
                MainNavigation.setSelected(it, view.id)
                if (!(this is AboutMeFragment)) {
                    findNavController().popBackStack(R.id.aboutMeFragment, false)
                }
            }
        }

        it.findViewById<View>(R.id.menu_item_skills)?.let { view ->

            view.setOnClickListener { _ ->
                MainNavigation.setSelected(it, view.id)
                if (!(this is SkillsListFragment)) {
                    findNavController().popBackStack(R.id.aboutMeFragment, false)
                    findNavController().navigate(R.id.action_aboutMeFragment_to_skillsListFragment)
                }
            }
        }

        it.findViewById<View>(R.id.menu_item_personal_projects)?.let { view ->

            view.setOnClickListener { _ ->
                MainNavigation.setSelected(it, view.id)
                if (!(this is PersonalProjectsFragment)) {
                    findNavController().popBackStack(R.id.aboutMeFragment, false)
                    findNavController().navigate(R.id.action_aboutMeFragment_to_personalProjectsFragment)
                }
            }
        }

        it.findViewById<View>(R.id.menu_item_about_app)?.let { view ->

            view.setOnClickListener { _ ->
                MainNavigation.setSelected(it, view.id)
                if (!(this is AboutAppFragment)) {
                    findNavController().popBackStack(R.id.aboutMeFragment, false)
                    findNavController().navigate(R.id.action_aboutMeFragment_to_aboutAppFragment)
                }
            }
        }

        it.findViewById<View>(R.id.menu_item_contact)?.let { view ->

            view.setOnClickListener { _ ->
                MainNavigation.setSelected(it, view.id)
                if (!(this is ContactTypeFragment)) {
                    findNavController().popBackStack(R.id.aboutMeFragment, false)
                    findNavController().navigate(R.id.action_aboutMeFragment_to_contactTypeFragment)
                }
            }
        }
    }
}


















































