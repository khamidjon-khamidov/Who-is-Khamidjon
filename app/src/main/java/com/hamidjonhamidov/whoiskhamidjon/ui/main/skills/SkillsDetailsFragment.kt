package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hamidjonhamidov.whoiskhamidjon.R

/**
 * A simple [Fragment] subclass.
 */
class SkillsDetailsFragment : Fragment() {

    private val TAG = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SkillsDetailsFragment: onCreate: ")
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skills_details, container, false)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SkillsDetailsFragment: onDestroy: ")
    }
}
