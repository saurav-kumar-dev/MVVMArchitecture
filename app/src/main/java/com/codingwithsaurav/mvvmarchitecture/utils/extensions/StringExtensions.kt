package com.codingwithsaurav.mvvmarchitecture.utils.extensions

import java.net.MalformedURLException
import java.net.URL
import java.util.*


val String.Companion.empty: String get() = ""

fun String?.safeGet(): String = this ?: String.empty

fun String.nonAlphabetCharPresent(): Boolean = this.matches("^[a-zA-Z]*$".toRegex()).not()

fun String.getNumbers(): Int = Regex("[^0-9]").replace(this, "").toInt()

fun String.removeWhiteSpaces(): String = this.replace("\\s".toRegex(), "")

fun String.Companion.randomString(length: Int): String {
    val allowedChars = "0123456789"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun String.getUrlFromString(): String {
    val p = android.util.Patterns.WEB_URL
    val m = p.matcher(this)
    try {
        if (m.find()) {
            val furl = m.group()
            return furl.lowercase(Locale.ROOT)
        }
    } catch (ignored: Exception) {
    }
    return this
}

fun String.getBaseUrl(): String {
    return try {
        val url = URL(this)
        val baseUrl = url.host
        if (baseUrl.startsWith("www.")) {
            baseUrl.replace("www.", "")
        }
        baseUrl
    } catch (e: MalformedURLException) {
        this
    }
}