package me.kariot.wallpaperapp.utils.extensions

import android.widget.ImageView
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import me.kariot.wallpaperapp.R

fun ImageView.fromUrl(url: String?) {
    this.load(url) {
        crossfade(true).listener(onError = { request, throwable ->
            throwable.printStackTrace()
        })
        placeholder(getPlaceHolder())
        error(R.drawable.ic_placeholder)
    }
}

fun getPlaceHolder(): ShimmerDrawable {
    val shimmer =
        Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setDuration(1800) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
    return ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}
