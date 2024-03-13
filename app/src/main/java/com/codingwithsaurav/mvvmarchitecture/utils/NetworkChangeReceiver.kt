package com.codingwithsaurav.mvvmarchitecture.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (isOnline(p0!!)) {
        } else {
            val intent = Intent( "action_connection_status");
            p0.sendBroadcast(intent);
        }
    }

    private fun isOnline(context: Context): Boolean {
        val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        val netInfo: android.net.NetworkInfo? = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}