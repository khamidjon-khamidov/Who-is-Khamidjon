package com.hamidjonhamidov.whoiskhamidjon.ui

interface DataStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)

    fun shouldStartShimmerInFragment(shouldAnimate: Boolean)

    fun lockDrawer(isLocked: Boolean, menuId: Int)

    fun closeLeftNavigationMenu()
}