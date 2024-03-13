package com.codingwithsaurav.mvvmarchitecture.utils.extensions

import android.view.View

fun View.hide() {
    visibility = View.GONE
}


fun View.show(show: Boolean = true) {
    visibility = if (show)
        View.VISIBLE
    else
        View.GONE

}


fun View.gone(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}