package com.hamidjonhamidov.whoiskhamidjon.ui

import android.app.Activity
import android.view.View
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.main.MainActivity

fun Activity.displaySnackbar(message: String){
    message.hashCode()
    // todo
}

fun Activity.displaySuccessDialog(message: String){
    message.hashCode()
    // todo
}

fun Activity.displayErrorDialog(message: String){
    message.hashCode()
    // todo
}

fun MainActivity.setListenersForLeftDrawer(listener: View.OnClickListener){
    findViewById<View>(R.id.menu_item_about_me).setOnClickListener(listener)
    findViewById<View>(R.id.menu_item_skills).setOnClickListener(listener)
    findViewById<View>(R.id.menu_item_personal_projects).setOnClickListener(listener)
//    findViewById<View>(R.id.menu_item_source_code).setOnClickListener(listener)
    findViewById<View>(R.id.menu_item_about_app).setOnClickListener(listener)
//    findViewById<View>(R.id.menu_item_personal_posts).setOnClickListener(listener)
    findViewById<View>(R.id.menu_item_contact).setOnClickListener(listener)
}










