package com.hamidjonhamidov.whoiskhamidjon.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setActionBarTitle(title: String){
    activity?.let {
        it as AppCompatActivity
        it.supportActionBar?.setTitle(title)
    }
}