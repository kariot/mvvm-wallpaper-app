package me.kariot.wallpaperapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object ImageUtils {
    fun urlToBitmap(
        context: Context,
        url: String?,
        success: (Bitmap) -> Unit,
        error: (String) -> Unit
    ) {
        val loader = ImageLoader(context)
        val req = ImageRequest.Builder(context)
            .data(url)
            .target(onSuccess = { result ->
                val bitmap = (result as BitmapDrawable).bitmap
                success(bitmap)
            },
                onError = {
                    error("Failed to download image.")
                }
            )
            .listener(onError = { request, throwable ->
                throwable.printStackTrace()
            }
            )
            .build()
        loader.enqueue(req)
    }

    fun saveImageToStorage(context: Context, bitmap: Bitmap, id: Int?): Boolean {
        return try {
            context.openFileOutput("$id.jpg", AppCompatActivity.MODE_PRIVATE)
                .use { stream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                        throw IOException("Could not save image")
                    }
                }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getImageUri(context: Context, id: Int?): Uri? {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            val filteredFiles =
                files?.filter {
                    it.canRead() && it.isFile && it.name.contains("$id") && it.name.endsWith(
                        ".jpg"
                    )
                }?.map {
                    it.toUri()
                }
            if (filteredFiles.isNullOrEmpty()) {
                null
            } else {
                filteredFiles.first()
            }
        }
    }
}