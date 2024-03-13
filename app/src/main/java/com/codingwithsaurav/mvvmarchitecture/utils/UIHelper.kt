package com.codingwithsaurav.mvvmarchitecture.utils

import android.content.Context
import android.text.format.DateFormat
import java.util.*

open class UIHelper {

    companion object {

        fun timeToDate(context: Context, date: Date): String? {
            val dateFormat = DateFormat.getDateFormat(context)
            return dateFormat.format(date)
        }
    }


}