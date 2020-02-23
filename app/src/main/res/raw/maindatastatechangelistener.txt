package com.hamidjonhamidov.whoiskhamidjon.ui.main

interface MainDataStateChangeListener {
    fun shouldStartShimmerInFragment(shouldAnimate: Boolean)

    fun lockDrawer(isLocked: Boolean, menuId: Int)

    fun closeLeftNavigationMenu()
}