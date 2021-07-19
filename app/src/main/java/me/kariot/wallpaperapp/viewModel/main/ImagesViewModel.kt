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

    private fun getImages() {
        imagesResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = repo.getImages(currentPage)
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    val oldList = ((images.value?.data)?.toMutableList() ?: mutableListOf())
                    oldList.addAll(response.photos ?: emptyList())
                    images.postValue(Resource.Success(oldList))
                } ?: Resource.Error("An error occurred", response.body())
            } else {
                Resource.Error(response.errorBody()?.toString() ?: "An error occurred", null)
            }
        }
    }
}