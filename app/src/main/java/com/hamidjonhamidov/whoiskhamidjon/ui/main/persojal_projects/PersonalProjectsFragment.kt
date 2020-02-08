package com.hamidjonhamidov.whoiskhamidjon.ui.main.persojal_projects

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

class PersonalProjectsFragment : Fragment(), View.OnClickListener {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "PersonalProjectsFragment: onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun initialize(){
        setLeftDrawerListeners()
    }

    override fun onClick(v: View?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "PersonalProjectsFragment: onDestroy: ")
    }
}
