package com.codingwithsaurav.mvvmarchitecture

import android.app.Application
import android.content.IntentFilter
import android.util.Log
import com.codingwithsaurav.mvvmarchitecture.utils.NetworkChangeReceiver
import com.codingwithsaurav.mvvmarchitecture.utils.Utils
import com.codingwithsaurav.mvvmarchitecture.utils.extensions.empty
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        //FirebaseApp.initializeApp(this)
        getFirebaseToken()
        //Internet connection BroadCast Receiver
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(NetworkChangeReceiver(), filter)
    }

    fun getFirebaseToken(onError: (() -> Unit)? = null, onGetToken: (() -> Unit)? = null) {
        /*if (Utils.hasInternetConnection()) {
            CoroutineScope(Dispatchers.IO).launch {
                FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
                    firebaseUser.getIdToken(false).addOnCompleteListener {
                        if (it.isSuccessful) {
                            if(it.result.token.isNullOrBlank().not()){
                                firebaseToken = it.result.token!!
                                // Log.e("token", firebaseToken)
                                onGetToken?.invoke()
                            }else{
                                Log.e(TAG, "FirebaseAuth : firebaseToken result token is null or blank")
                                onError?.invoke()
                            }
                        } else {
                            Log.e(TAG, "FirebaseAuth : firebaseToken task failed")
                            onError?.invoke()
                        }
                    }.addOnFailureListener {
                        Log.e(TAG, "FirebaseAuth : firebaseToken  failed ${it.message}")
                        onError?.invoke()
                    }
                } ?: run {
                    Log.d(TAG, "FirebaseAuth :  current user is  null, logout perform")
                    onError?.invoke()
                }
            }
        } else {
            onError?.invoke()
        }*/
    }

    private fun clearPushInstanceId() {
        try {
            //FirebaseInstallations.getInstance().delete()
            //FirebaseMessaging.getInstance().deleteToken()
        } catch (e1: Exception) {
        }
    }

    fun getGson(): Gson {
        if (gson == null) {
            gson = GsonBuilder().create()
        }
        return gson!!
    }

    companion object {
        const val TAG = "MovieApp"
        lateinit var instance: MovieApp
        var firebaseToken: String = ""
        private var gson: Gson? = null
    }
}