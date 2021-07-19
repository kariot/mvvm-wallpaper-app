package me.kariot.wallpaperapp.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.kariot.wallpaperapp.model.responseModel.PhotosResponseModel
import me.kariot.wallpaperapp.repo.MainRepository
import me.kariot.wallpaperapp.utils.Resource

class ImagesViewModel(val repo: MainRepository) : ViewModel() {
    val imagesResponse: MutableLiveData<Resource<PhotosResponseModel>> = MutableLiveData()
    val currentPage = 1

    init {
        getImages()
    }

    private fun getImages() {
        imagesResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = repo.getImages(currentPage)
            if (response.isSuccessful) {
                response.body()?.let {
                    imagesResponse.postValue(Resource.Success(it))
                } ?: Resource.Error("An error occurred", response.body())
            } else {
                Resource.Error(response.errorBody()?.toString() ?: "An error occurred", null)
            }
        }
    }
}