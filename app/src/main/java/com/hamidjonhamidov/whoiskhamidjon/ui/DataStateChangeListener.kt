package com.hamidjonhamidov.whoiskhamidjon.ui

interface DataStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)

    fun shouldStartShimmerInFragment(shouldAnimate: Boolean)
}