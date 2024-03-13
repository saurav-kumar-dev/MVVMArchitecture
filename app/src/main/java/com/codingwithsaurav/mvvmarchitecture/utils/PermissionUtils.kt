package com.codingwithsaurav.mvvmarchitecture.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.codingwithsaurav.mvvmarchitecture.MovieApp

object PermissionUtils {

    const val CAMERA_PERMISSION_CODE = 0x12132
    const val STORAGE_PERMISSION_CODE = 0x42423
    const val NOTIFICATION_PERMISSION_CODE = 0x13432432

    fun hasStoragePermission(): Boolean {
        return if (Utils.androidRAndAbove) {
            ContextCompat.checkSelfPermission(
                MovieApp.instance,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                MovieApp.instance,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            MovieApp.instance,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }


    fun allGranted(grantResults: IntArray): Boolean {
        if (grantResults.isEmpty()) {
            return false
        }
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    fun getFirstDenied(grantResults: IntArray, permissions: Array<String>): String? {
        for (i in grantResults.indices) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                return permissions[i]
            }
        }
        return null
    }

    fun isCameraAllowed(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkCameraPermission(
        activity: Activity,
        cameracode: Int = CAMERA_PERMISSION_CODE
    ): Boolean {
        return if (!isCameraAllowed(activity)) {
            activity.requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA
                ),
                cameracode
            )
            false
        } else
            true
    }


    fun isStorageAllowed(activity: Activity): Boolean {
        return if (Utils.android13AndAbove) {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else
            (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED)
    }

    fun checkExternalStorageReadWrite(
        activity: Activity,
        storagecode: Int = STORAGE_PERMISSION_CODE
    ): Boolean {
        return if (!isStorageAllowed(activity)) {
            val permissionList =
                if (Utils.android13AndAbove) {
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                } else
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
            activity.requestPermissions(
                permissionList,
                storagecode
            )
            false
        } else
            true
    }

    private fun isNotificationAllowed(activity: Activity): Boolean {
        return if (Utils.android13AndAbove) {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else
            true
    }

    fun askPostNotification(
        activity: Activity,
        storagecode: Int = NOTIFICATION_PERMISSION_CODE
    ): Boolean {
        return if (Utils.android13AndAbove && !isNotificationAllowed(activity)) {
            val permissionList =
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            activity.requestPermissions(
                permissionList,
                storagecode
            )
            false
        } else
            true
    }

}