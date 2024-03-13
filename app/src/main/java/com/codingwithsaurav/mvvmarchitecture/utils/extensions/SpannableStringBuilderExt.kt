package com.codingwithsaurav.mvvmarchitecture.utils.extensions

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan


fun SpannableStringBuilder.setBoldSpan(mainString:String, subString:String){
    val spanStartIndex = mainString.indexOf(subString)
    if(spanStartIndex != -1 ) {
        val spanEndIndex = spanStartIndex + subString.length
        setSpan(
            StyleSpan(Typeface.BOLD), spanStartIndex, spanEndIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }
}


fun SpannableStringBuilder.setWhiteColorSpan(mainString:String, subString:String){
    val spanStartIndex = mainString.indexOf(subString)
    if(spanStartIndex != -1 ) {
        val spanEndIndex = spanStartIndex + subString.length
        setSpan(
            ForegroundColorSpan(Color.WHITE), spanStartIndex, spanEndIndex,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }
}