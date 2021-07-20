package me.kariot.wallpaperapp.utils.extensions

import android.view.View
import android.view.View.*

fun View.show() {
    this.visibility = VISIBLE
}

fun View.hide(keepSpace: Boolean = false) {
    this.visibility = if (keepSpace) {
        INVISIBLE
    } else {
        GONE
    }
}