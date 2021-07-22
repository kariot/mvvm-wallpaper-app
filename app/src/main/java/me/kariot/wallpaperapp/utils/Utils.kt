package me.kariot.wallpaperapp.utils

import android.app.Activity
import android.util.DisplayMetrics


object Utils {
    fun getDeviceWidth(context: Activity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getDeviceHeight(context: Activity): Int {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}