package me.kariot.wallpaperapp.api

import me.kariot.wallpaperapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${Constants.API_KEY}").build()
                chain.proceed(request)
            }.build()
            Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        val imagesApi by lazy {
            retrofit.create(ImagesAPI::class.java)
        }
    }
}