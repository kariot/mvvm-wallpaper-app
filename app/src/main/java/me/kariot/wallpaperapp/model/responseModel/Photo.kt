package me.kariot.wallpaperapp.model.responseModel


import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("avg_color")
    var avgColor: String?, // #80866B
    @SerializedName("height")
    var height: Int?, // 4000
    @SerializedName("id")
    var id: Int?, // 2740915
    @SerializedName("photographer")
    var photographer: String?, // SLAYTINA
    @SerializedName("photographer_id")
    var photographerId: Int?, // 1381973
    @SerializedName("photographer_url")
    var photographerUrl: String?, // https://www.pexels.com/@slaytina-1381973
    @SerializedName("src")
    var src: Src?,
    @SerializedName("url")
    var url: String?, // https://www.pexels.com/photo/fashion-man-people-woman-2740915/
    @SerializedName("width")
    var width: Int? // 3000
)