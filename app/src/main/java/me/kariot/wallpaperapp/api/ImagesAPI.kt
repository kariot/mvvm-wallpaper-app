package me.kariot.wallpaperapp.api

import me.kariot.wallpaperapp.model.responseModel.PhotosResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesAPI {
    @GET("search/")
    suspend fun getImages(
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int = 20,
        @Query("query")
        query: String = "mobile wallpaper"
    ): Response<PhotosResponseModel>
}