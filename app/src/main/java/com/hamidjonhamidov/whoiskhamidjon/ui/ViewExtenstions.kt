package com.hamidjonhamidov.whoiskhamidjon.ui

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity
import com.hamidjonhamidov.whoiskhamidjon.util.OnSnackbarClicked

class ViewExtenstions {}

fun Activity.displaySnackbar(view: View, message: String, action: String, listener: OnSnackbarClicked){

    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

    snackbar.setAction(action) {
            listener.onSnackbarClick(snackbar)
        }.show()
}

fun Activity.displaySuccessDialog(message: String){
    message.hashCode()
    // todo
}

fun Activity.displayErrorDialog(message: String){
    message.hashCode()
    // todo
}











