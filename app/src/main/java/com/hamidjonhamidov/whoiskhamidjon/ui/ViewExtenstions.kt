package com.hamidjonhamidov.whoiskhamidjon.ui

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.hamidjonhamidov.whoiskhamidjon.R
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

fun Activity.displayDialog(
    okBtnText: String,
    noBtnTxt: String,
    title: String,
    body: String,
    cancelable: Boolean,
    listener: DialogButtonClickListener
): Dialog{

    val dialog = Dialog(this)
    dialog.setCancelable(cancelable)
    dialog.setContentView(R.layout.custom_dialog)

    // binding views in custom_dialog
    val titleTv = dialog.findViewById<TextView>(R.id.tv_title_custom_dialog)
    val bodyTv = dialog.findViewById<TextView>(R.id.tv_body_custom_dialog)
    val okBtn = dialog.findViewById<TextView>(R.id.btn_ok_custom_dialog)
    val noBtn = dialog.findViewById<TextView>(R.id.btn_no_custom_dialog)

    // setting dialog texts
    titleTv.text = title
    bodyTv.text = body
    okBtn.text = okBtnText
    noBtn.text = noBtnTxt

    // setting listeners
    okBtn.setOnClickListener { listener.onOkClick(dialog) }
    noBtn.setOnClickListener { listener.onNoClick(dialog) }

    dialog.show()

    return dialog
}

// interface for dialog "ok" and "no" buttons
interface DialogButtonClickListener{
    fun onOkClick(dialog: Dialog)

    fun onNoClick(dialog: Dialog)
}









