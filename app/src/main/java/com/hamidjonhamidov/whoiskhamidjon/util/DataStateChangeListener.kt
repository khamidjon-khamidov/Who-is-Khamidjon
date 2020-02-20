package com.hamidjonhamidov.whoiskhamidjon.util

import com.hamidjonhamidov.whoiskhamidjon.ui.DataState

interface DataStateChangeListener {
    fun onDataStateChange(dataState: DataState<*>?)
}