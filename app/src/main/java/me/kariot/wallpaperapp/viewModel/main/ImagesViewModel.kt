package me.kariot.wallpaperapp.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kariot.wallpaperapp.model.responseModel.Photo
import me.kariot.wallpaperapp.model.responseModel.PhotosResponseModel
import me.kariot.wallpaperapp.repo.MainRepository
import me.kariot.wallpaperapp.utils.Resource

class ImagesViewModel(val repo: MainRepository) : ViewModel() {
    val imagesResponse: MutableLiveData<Resource<PhotosResponseModel>> = MutableLiveData()
    val images: MutableLiveData<Resource<MutableList<Photo>>> = MutableLiveData()
    private var currentPage = 1

    init {
        getImages()
    }

    fun loadNextPage() {
        currentPage++
        getImages()
    }

    fun getImages() {
        imagesResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = repo.getImages(currentPage)
                if (response.isSuccessful) {
                    response.body()?.let { response ->
                        val oldList = ((images.value?.data)?.toMutableList() ?: mutableListOf())
                        oldList.addAll(response.photos ?: emptyList())
                        images.postValue(Resource.Success(oldList))
                    } ?: Resource.Error("An error occurred", response.body())
                } else {
                    images.postValue(
                        Resource.Error(
                            response.errorBody()?.toString() ?: "An error occurred", null
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                imagesResponse.postValue(Resource.Error(e.message ?: "An error occurred", null))
            }
        }
    }

    fun isFirstRequest() = currentPage == 1
}