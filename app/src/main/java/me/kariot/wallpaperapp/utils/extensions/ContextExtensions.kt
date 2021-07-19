package me.kariot.wallpaperapp.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrl(url: String?) {
    url?.let {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}