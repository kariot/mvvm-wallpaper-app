package me.kariot.wallpaperapp.model.responseModel


import com.google.gson.annotations.SerializedName

data class PhotosResponseModel(
    @SerializedName("next_page")
    var nextPage: String?, // https://api.pexels.com/v1/curated/?page=2&per_page=15
    @SerializedName("page")
    var page: Int?, // 1
    @SerializedName("per_page")
    var perPage: Int?, // 15
    @SerializedName("photos")
    var photos: List<Photo>?,
    @SerializedName("total_results")
    var totalResults: Int? // 8000
)