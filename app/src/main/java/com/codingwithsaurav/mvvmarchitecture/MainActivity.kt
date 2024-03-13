package com.codingwithsaurav.mvvmarchitecture

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.codingwithsaurav.mvvmarchitecture.utils.extensions.showSmallSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(internetConnectionStatus, IntentFilter("action_connection_status"))
        MovieApp.instance.getFirebaseToken(
            onError = {
            },
            onGetToken = {
            }
        )
    }

    private var internetConnectionStatus: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            lifecycleScope.launch(Dispatchers.Main) {
                showSmallSnackbar(getString(R.string.internet_connection_status))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(internetConnectionStatus);
    }
}