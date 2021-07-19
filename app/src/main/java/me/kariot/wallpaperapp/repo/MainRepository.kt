package me.kariot.wallpaperapp.repo

import me.kariot.wallpaperapp.api.RetrofitInstance

class MainRepository {
    suspend fun getImages(page: Int) = RetrofitInstance.imagesApi.getImages(page)
}