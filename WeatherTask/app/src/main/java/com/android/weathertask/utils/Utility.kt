package com.android.weathertask.utils

import android.util.Log
import com.android.weathertask.BuildConfig

object Utility {

    private val TAG = Utility::class.java.simpleName

    //log with tag and value
    fun log(tag: String, value: String) {
        if (BuildConfig.DEBUG)
            Log.e(tag, value)
    }

    //log with value
    fun log(value: String) {
        if (BuildConfig.DEBUG) {
            log(TAG, value)
            // logFile(value);
        }
    }

}