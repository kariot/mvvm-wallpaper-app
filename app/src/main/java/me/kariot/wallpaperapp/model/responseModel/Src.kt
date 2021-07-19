package me.kariot.wallpaperapp.model.responseModel


import com.google.gson.annotations.SerializedName

data class Src(
    @SerializedName("landscape")
    var landscape: String?, // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200
    @SerializedName("large")
    var large: String?, // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg?auto=compress&cs=tinysrgb&h=650&w=940
    @SerializedName("large2x")
    var large2x: String?, // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940
    @SerializedName("medium")
    var medium: String?, // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg?auto=compress&cs=tinysrgb&h=350
    @SerializedName("original")
    var original: String?, // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg
    @SerializedName("portrait")
    var portrait: String?, // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800
    @SerializedName("small")
    var small: String?, // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg?auto=compress&cs=tinysrgb&h=130
    @SerializedName("tiny")
    var tiny: String? // https://images.pexels.com/photos/2740915/pexels-photo-2740915.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280
)