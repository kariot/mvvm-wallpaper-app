package me.kariot.wallpaperapp.utils.extensions

import android.util.Log
import android.widget.ImageView
import coil.load
import me.kariot.wallpaperapp.R

fun ImageView.fromUrl(url: String?) {
    Log.d("Loaded", url!!)
    this.load(url) {
        crossfade(true).listener(onError = { request, throwable ->
            throwable.printStackTrace()
        })
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }
}