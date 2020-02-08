package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import com.hamidjonhamidov.whoiskhamidjon.util.MainNavigation
import com.hamidjonhamidov.whoiskhamidjon.util.setLeftDrawerListeners

class SkillsListFragment : Fragment(), View.OnClickListener {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SkillsListFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skills_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun initialize(){
        setLeftDrawerListeners()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.menu_item_about_me -> {
                findNavController().popBackStack()
                activity?.let {
                    MainNavigation.setSelected(it as MainActivity, v.id)
                }
            }

            R.id.menu_item_skills -> {
                activity?.let {
                    MainNavigation.setSelected(it as MainActivity, v.id)
                }
            }

            R.id.menu_item_personal_projects -> {
                findNavController().popBackStack()
                findNavController().navigate(R.id.action_aboutMeFragment_to_personalProjectsFragment)
                activity?.let {
                    MainNavigation.setSelected(it as MainActivity, v.id)
                }
            }

            R.id.menu_item_about_app -> {
                findNavController().navigate(R.id.action_aboutMeFragment_to_aboutAppFragment)
                activity?.let {
                    MainNavigation.setSelected(it as MainActivity, v.id)
                }
            }

            R.id.menu_item_contact -> {
                findNavController().navigate(R.id.action_contactTypeFragment_to_contactFragment)
                activity?.let {
                    MainNavigation.setSelected(it as MainActivity, v.id)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SkillsListFragment: onDestroy: ")
    }
}
