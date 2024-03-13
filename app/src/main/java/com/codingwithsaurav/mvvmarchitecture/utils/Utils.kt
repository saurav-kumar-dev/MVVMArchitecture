package com.codingwithsaurav.mvvmarchitecture.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.core.content.ContextCompat
import com.codingwithsaurav.mvvmarchitecture.MovieApp
import com.codingwithsaurav.mvvmarchitecture.utils.FileUtils.calculateInSampleSize
import com.codingwithsaurav.mvvmarchitecture.utils.extensions.safeGet
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.regex.Pattern


object Utils {
    val androidMarshMellow: Boolean
        get() = Build.VERSION.SDK_INT == Build.VERSION_CODES.M

    val androidNaugatAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    val androidOreoAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    val androidPieAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

    val androidQAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    val androidRAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    val androidSAndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= 31

    val android13AndAbove: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU



    fun compressImage(filePath: String?): File? {
        try {
            var scaledBitmap: Bitmap? = null
            val options =
                BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var bmp =
                BitmapFactory.decodeFile(filePath, options)
            var actualHeight = options.outHeight
            var actualWidth = options.outWidth
            val maxHeight = 816.0f
            val maxWidth = 612.0f
            var imgRatio = actualWidth / actualHeight.toFloat()
            val maxRatio = maxWidth / maxHeight
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()
                }
            }
            options.inSampleSize =
                calculateInSampleSize(options, actualWidth, actualHeight)
            options.inJustDecodeBounds = false
            options.inDither = false
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(2 * 1048576)
            try {
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            try {
                scaledBitmap = Bitmap.createBitmap(
                    actualWidth,
                    actualHeight,
                    Bitmap.Config.ARGB_8888
                )
            } catch (exception: OutOfMemoryError) {
                exception.printStackTrace()
            }
            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f
            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
            val canvas: Canvas
            if (scaledBitmap != null) {
                canvas = Canvas(scaledBitmap)
                canvas.setMatrix(scaleMatrix)
                canvas.drawBitmap(
                    bmp,
                    middleX - bmp.width / 2,
                    middleY - bmp.height / 2,
                    Paint(Paint.FILTER_BITMAP_FLAG)
                )
            }
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath.safeGet())
                val orientation =
                    exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
                val matrix = Matrix()
                when (orientation) {
                    6 -> {
                        matrix.postRotate(90f)
                    }
                    3 -> {
                        matrix.postRotate(180f)
                    }
                    8 -> {
                        matrix.postRotate(270f)
                    }
                }
                if (scaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(
                        scaledBitmap,
                        0,
                        0,
                        scaledBitmap.width,
                        scaledBitmap.height,
                        matrix,
                        true
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val out: FileOutputStream
            try {
                out = FileOutputStream(filePath)
                scaledBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return File(filePath)
    }


    fun getAppropriatePendingIntent(
        mutableIntent: Boolean = false, pendingIntent: Int = PendingIntent.FLAG_UPDATE_CURRENT
    ): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (mutableIntent) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_IMMUTABLE
        } else {
            pendingIntent
        }
    }

    fun hasInternetConnection(): Boolean {
        val cm =
            MovieApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return try {
            val activeNetwork =
                cm?.activeNetworkInfo
            activeNetwork != null && (activeNetwork.isConnected || activeNetwork.type == ConnectivityManager.TYPE_ETHERNET)
        } catch (e: RuntimeException) {
            true //if internet connection can not be checked it is probably best to just try
        }
    }

    fun getColoredSpannableString(
        context: Context, mainString: String, span: String,
        color: Int, isBoldRequired: Boolean = false,
        isUnderLineRequired: Boolean = false
    ): SpannableStringBuilder {
        val spanStartIndex = mainString.indexOf(span)
        val spanEndIndex = spanStartIndex + span.length
        val spannableString = SpannableStringBuilder(mainString)
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color)),
            spanStartIndex, spanEndIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        if (isBoldRequired) {
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                spanStartIndex, spanEndIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        if (isUnderLineRequired) {
            spannableString.setSpan(
                UnderlineSpan(), spanStartIndex, spanEndIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        return spannableString
    }


    fun getColoredBoldSpannableString(
        context: Context, mainString: String, color: Int, spanList: ArrayList<String>,
    ): SpannableStringBuilder {
        val spannableString = SpannableStringBuilder(mainString)
        for (element in spanList) {
            val span = element
            val spanStartIndex = mainString.indexOf(span)
            val spanEndIndex = spanStartIndex + span.length
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, color)),
                spanStartIndex, spanEndIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                spanStartIndex, spanEndIndex,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        return spannableString
    }


    inline fun <reified T : java.io.Serializable> getSerializable(
        intent: Intent, key: String, modelClass: Class<T>
    ): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(key, modelClass)
        } else {
            intent.getSerializableExtra(key) as? T
        }
    }

    inline fun <reified T : java.io.Serializable> getSerializable(
        bundle: Bundle, key: String, modelClass: Class<T>
    ): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getSerializable(key, modelClass)
        } else {
            bundle.getSerializable(key) as? T
        }
    }

    class CustomInputFilter : InputFilter {

        private val regex = Pattern.compile("^(?=.{3,30}\$)([a-z0-9._])*\$")

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val matcher = regex.matcher(source)
            return if (matcher.find()) {
                null
            } else {
                ""
            }
        }
    }

}