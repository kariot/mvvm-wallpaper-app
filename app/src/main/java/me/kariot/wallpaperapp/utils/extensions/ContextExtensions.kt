package me.kariot.wallpaperapp.utils.extensions

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.openUrl(url: String?) {
    url?.let {
        var customTabsIntent: CustomTabsIntent = CustomTabsIntent.Builder().build();
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}