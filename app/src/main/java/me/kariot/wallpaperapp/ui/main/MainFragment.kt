package me.kariot.wallpaperapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import me.kariot.wallpaperapp.databinding.FragmentMainBinding
import me.kariot.wallpaperapp.databinding.NetworkViewBinding
import me.kariot.wallpaperapp.repo.MainRepository
import me.kariot.wallpaperapp.ui.base.BaseFragment
import me.kariot.wallpaperapp.utils.Resource
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