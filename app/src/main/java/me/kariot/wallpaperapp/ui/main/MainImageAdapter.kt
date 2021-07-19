package me.kariot.wallpaperapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.kariot.wallpaperapp.databinding.ListImageItemBinding
import me.kariot.wallpaperapp.model.responseModel.Photo
import me.kariot.wallpaperapp.utils.extensions.fromUrl
import me.kariot.wallpaperapp.utils.extensions.openUrl

class MainImageAdapter(val endOfListCallback: () -> Unit) :
    ListAdapter<Photo, MainImageAdapter.ImageIemVH>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageIemVH {
        val view = ListImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageIemVH(view)
    }

    override fun onBindViewHolder(holder: ImageIemVH, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
        if (position == currentList.size - 1) {
            endOfListCallback()
        }
    }

    override fun getItemCount(): Int = currentList.size

    inner class ImageIemVH(val bindingView: ListImageItemBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(photo: Photo?) {
            photo?.let { photo ->
                with(bindingView) {
                    imageView.fromUrl(photo.src?.portrait)
                    tvAuthor.text = photo.photographer
                    tvDimension.text = "${photo.width}x${photo.height}"
                    tvAuthor.setOnClickListener {
                        it.context.openUrl(photo.photographerUrl)
                    }
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem == newItem

    }


}