package me.kariot.wallpaperapp.viewModel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.kariot.wallpaperapp.repo.MainRepository

class ImageViewModelProviderFactory(val repo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImagesViewModel(repo) as T
    }
}