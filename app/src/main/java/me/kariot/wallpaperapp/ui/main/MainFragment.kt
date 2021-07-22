package me.kariot.wallpaperapp.ui.main

import android.app.Activity
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.coroutines.launch
import me.kariot.wallpaperapp.databinding.FragmentMainBinding
import me.kariot.wallpaperapp.databinding.NetworkViewBinding
import me.kariot.wallpaperapp.repo.MainRepository
import me.kariot.wallpaperapp.ui.base.BaseFragment
import me.kariot.wallpaperapp.utils.ImageUtils
import me.kariot.wallpaperapp.utils.Resource
import me.kariot.wallpaperapp.utils.Utils
import me.kariot.wallpaperapp.utils.extensions.hide
import me.kariot.wallpaperapp.utils.extensions.show
import me.kariot.wallpaperapp.viewModel.main.ImageViewModelProviderFactory
import me.kariot.wallpaperapp.viewModel.main.ImagesViewModel


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val imagesAdapter: MainImageAdapter by lazy {
        MainImageAdapter {
            imagesViewModel.loadNextPage()
        }
    }
    private val imagesViewModel by lazy {
        val repo = MainRepository()
        val factory = ImageViewModelProviderFactory(repo)
        ViewModelProvider(this, factory).get(ImagesViewModel::class.java)
    }
    private var _networkView: NetworkViewBinding? = null
    private val networkView: NetworkViewBinding get() = _networkView!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _networkView = NetworkViewBinding.bind(binding.root)
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
                    initError(it.message)
                }
            }
        })
        binding.fabWallpaper.setOnClickListener {
            val currentWallpaper = imagesAdapter.currentList[binding.vpImages.currentItem]
            ImageUtils.urlToBitmap(requireContext(), currentWallpaper.src?.portrait, { bitmap ->
                val isSuccess =
                    ImageUtils.saveImageToStorage(requireContext(), bitmap, currentWallpaper.id)
                if (isSuccess) {
                    startImageCropper(currentWallpaper.id)
                }
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun startImageCropper(id: Int?) {
        lifecycleScope.launch {
            CropImage.activity(ImageUtils.getImageUri(requireContext(), id))
                .setAspectRatio(
                    Utils.getDeviceWidth(requireActivity()),
                    Utils.getDeviceHeight(requireActivity())
                )
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(requireContext(), this@MainFragment)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            val resultUri = result.uri
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        resultUri
                    )
                )
            } else {
                MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver,
                    resultUri
                )
            }
            updateDeviceWallpaper(bitmap)
        }
    }

    private fun updateDeviceWallpaper(bmp: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(requireContext())
        wallpaperManager.setBitmap(bmp)
        Toast.makeText(requireContext(), "Wallpaper updated", Toast.LENGTH_SHORT).show()
    }

    private fun initError(message: String?) {
        if (imagesViewModel.isFirstRequest()) {
            networkView.vError.show()
            networkView.btnRetry.setOnClickListener {
                imagesViewModel.getImages()
            }
            networkView.txtErrorMessage.text = message
            return
        }
        showErrorSnack(message)
    }

    private fun showErrorSnack(message: String?) {
        val snack = Snackbar.make(
            binding.root,
            message ?: "An error occurred while fetching data..!",
            Snackbar.LENGTH_INDEFINITE
        )
        with(snack) {
            setAction("RETRY") {
                imagesViewModel.getImages()
            }
            show()
        }
    }

    private fun hideProgress() {
        if (imagesViewModel.isFirstRequest()) {
            networkView.vLoading.hide()
            return
        }
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showLoading() {
        if (imagesViewModel.isFirstRequest()) {
            networkView.vLoading.show()
            return
        }
        binding.swipeRefreshLayout.isRefreshing = true
    }
}