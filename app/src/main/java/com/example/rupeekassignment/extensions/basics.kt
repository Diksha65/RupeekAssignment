package com.example.rupeekassignment.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast


/**
 * LOG ERROR
 */
fun logError(error : Throwable) {
    logError("GlobalLog", error)
}
fun logError(tag : String, exception: Throwable) {
    exception.printStackTrace()
    val text = exception.message ?: "NullMessage"
    Log.e(tag, text)
}

/**
 * LOG DEBUG
 */
fun logDebug(message : String?) {
    logDebug("GlobalLog", message)
}
fun logDebug(tag : String, message : String?) {
    val text = message ?: "NullMessage"
    Log.d(tag, text)
}

/**
 *  TOAST
 */
fun toast(context : Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

