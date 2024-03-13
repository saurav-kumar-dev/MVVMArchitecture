package com.codingwithsaurav.mvvmarchitecture.utils

import android.app.Activity


object ThemeUtil {
    
    fun setStatusBarBottomNavigationColor(activity: Activity, statusColor : Int, bottomNavigation : Int) {
        activity.window.apply {
            statusBarColor = statusColor
            navigationBarColor = bottomNavigation
        }

    }
}