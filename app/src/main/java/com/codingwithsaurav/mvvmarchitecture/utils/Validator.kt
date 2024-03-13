package com.codingwithsaurav.mvvmarchitecture.utils

import android.text.TextUtils
import android.util.Patterns

object Validator {

    @JvmStatic
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}