package me.kariot.wallpaperapp.utils.extensions

import android.widget.ImageView
import coil.load

fun ImageView.fromUrl(url: String?) {
    this.load(url)
}