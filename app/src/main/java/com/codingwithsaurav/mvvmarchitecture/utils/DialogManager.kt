package com.codingwithsaurav.mvvmarchitecture.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView
import com.codingwithsaurav.mvvmarchitecture.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject


class DialogManager @Inject constructor() {

    fun singleButtonDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = context.getString(R.string.ok),
        alertDialogListener: AlertDialogListener? = null,
        cancelable: Boolean,
    ) {
        try {
            val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            builder.setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(positiveButtonText) { dialogInterface, _ ->
                    alertDialogListener?.onPositiveButtonClicked()
                    dialogInterface?.dismiss()
                }

            val alertDialog = builder.create()
            alertDialog.show()
        } catch (e: IllegalStateException) {
            Log.e(TAG, "singleButtonDialog " + e.localizedMessage)
        }
    }


    fun twoButtonDialog(
        context: Context,
        title: String,
        message: String,
        spannedMessage: Boolean = false,
        spannedTitle: Boolean = false,
        positiveButtonText: String = context.getString(R.string.ok),
        negativeButtonText: String = context.getString(R.string.cancel),
        alertDialogListener: AlertDialogListener? = null,
    ) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        builder
            .setPositiveButton(positiveButtonText) { dialogInterface, _ ->
                alertDialogListener?.onPositiveButtonClicked()
                dialogInterface?.dismiss()
            }
            .setNegativeButton(negativeButtonText) { dialogInterface, _ ->
                alertDialogListener?.onNegativeButtonClicked()
                dialogInterface.dismiss()
            }

        if (spannedTitle) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setTitle(Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY))
            } else {
                @Suppress("DEPRECATION")
                builder.setTitle(Html.fromHtml(title))
            }
        } else {
            builder.setTitle(title)
        }

        if (spannedMessage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setMessage(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY))
            } else {
                @Suppress("DEPRECATION")
                builder.setMessage(Html.fromHtml(message))
            }
        } else {
            builder.setMessage(message)
        }

        val alertDialog = builder.create()
        alertDialog.setOnDismissListener { alertDialogListener?.onDialogDismissed() }
        alertDialog.show()
    }

    fun showChooseImageDialog(
        activity: Activity,
        listener: AlertDialogCameraListener
    ) {
        activity.run {
            val dialog = BottomSheetDialog(activity, R.style.TransparentBackgroundBottomSheetTheme)
            dialog.setContentView(R.layout.pop_up_upload_image)
            dialog.setCanceledOnTouchOutside(true)
            val btnGallery = dialog.findViewById<AppCompatTextView>(R.id.textViewGallery)
            val btnCamera = dialog.findViewById<AppCompatTextView>(R.id.textViewCamera)

            btnGallery!!.setOnClickListener {
                dialog.dismiss()
                listener.onGalleryClick()
            }
            btnCamera!!.setOnClickListener {
                dialog.dismiss()
                listener.onCameraClick()
            }
            dialog.show()
        }
    }

    interface AlertDialogListener {
        fun onPositiveButtonClicked() { }
        fun onNegativeButtonClicked() { }
        fun onNeutralButtonClicked() { }
        fun onDialogDismissed() { }
    }

    interface AlertDialogCameraListener {
        fun onCameraClick() { }
        fun onGalleryClick() { }
        fun onShownImageClick(uri: Uri) { }
    }

    interface AlertDialogItemClickListener {
        fun onItemClicked(which: Int) { }
        fun onItemClicked(which: String) { }
        fun onDialogDismissed() { }
    }
}