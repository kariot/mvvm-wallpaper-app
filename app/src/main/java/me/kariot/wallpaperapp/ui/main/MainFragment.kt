package me.kariot.wallpaperapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import me.kariot.wallpaperapp.databinding.FragmentMainBinding
import me.kariot.wallpaperapp.repo.MainRepository
import me.kariot.wallpaperapp.ui.base.BaseFragment
import me.kariot.wallpaperapp.utils.Resource
import me.kariot.wallpaperapp.viewModel.main.ImageViewModelProviderFactory
import me.kariot.wallpaperapp.viewModel.main.ImagesViewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val imagesAdapter: MainImageAdapter by lazy {
        MainImageAdapter {
            Toast.makeText(requireContext(), "Loads next page", Toast.LENGTH_SHORT).show()
            imagesViewModel.loadNextPage()
        }
    }
    private val imagesViewModel by lazy {
        val repo = MainRepository()
        val factory = ImageViewModelProviderFactory(repo)
        ViewModelProvider(this, factory).get(ImagesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.vpImages.apply {
            adapter = imagesAdapter
        }
        imagesViewModel.images.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideProgress()
                    imagesAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun hideProgress() {
        Toast.makeText(requireContext(), "Loading completed", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
    }
}