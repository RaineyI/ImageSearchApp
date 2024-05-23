package com.raineyi.imagesearchapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.raineyi.imagesearchapp.databinding.ImageItemBinding
import com.raineyi.imagesearchapp.domain.Image
import com.squareup.picasso.Picasso

class ImageListAdapter : ListAdapter<Image, ImageListViewHolder>(ImageDiffCallback) {

    var onLoadMoreListener: (() -> Unit)? = null
    var onImageClickListener: ((Image) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        val imageItem = getItem(position)
        val binding = holder.binding

        Picasso.get()
            .load(imageItem.imageUrl)
            .resize(300, 0)
            .fetch()

        Picasso.get()
            .load(imageItem.imageUrl)
            .resize(300, 0)
            .into(binding.imageCard)

        if (position >= currentList.size - 10) {
            onLoadMoreListener?.invoke()
        }

        binding.root.setOnClickListener {
            onImageClickListener?.invoke(imageItem)
        }
    }
}