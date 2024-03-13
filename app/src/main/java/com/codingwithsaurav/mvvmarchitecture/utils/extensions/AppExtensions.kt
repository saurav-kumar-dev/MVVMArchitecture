package com.codingwithsaurav.mvvmarchitecture.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.codingwithsaurav.mvvmarchitecture.R
import com.google.android.material.snackbar.Snackbar

var toast: Toast? = null
var snackbar: Snackbar? = null

fun Activity.showToast(context: Context, message: String) {
    toast?.cancel()
    toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT)
    toast!!.duration = Toast.LENGTH_SHORT
    toast!!.setText(message)
    toast!!.show()
}

fun Activity.showSmallSnackbar(message: String, parentView:View = this.window.decorView.findViewById(android.R.id.content)) {
    snackbar?.dismiss()
    snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).apply {
        view.setBackgroundColor(getColor(R.color.light_black))
        (view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).setTextColor(ContextCompat.getColor(this@showSmallSnackbar, R.color.intro_color))
        show()
    }
}

fun Activity.showLongSnackbar(message: String, parentView:View = this.window.decorView.findViewById(android.R.id.content)) {
    snackbar?.dismiss()
    snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).apply {
        view.setBackgroundColor(getColor(R.color.light_black))
        (view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).setTextColor(ContextCompat.getColor(this@showLongSnackbar, R.color.intro_color))
        show()
    }
}


fun Activity.showErrorSnackBar(message: String, parentView:View = this.window.decorView.findViewById(android.R.id.content)) {
    snackbar?.dismiss()
    snackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).apply {
        view.setBackgroundColor(getColor(R.color.light_black))
        (view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).setTextColor(ContextCompat.getColor(this@showErrorSnackBar, R.color.red_logout))
        show()
    }
}

fun ComponentActivity.addBackPressedCallback(handleCallback: (() -> Unit)? = null) {
    onBackPressedDispatcher.addCallback(
        this, // LifecycleOwner
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleCallback?.invoke()
            }
        }
    )
}


