package com.raineyi.imagesearchapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.raineyi.imagesearchapp.databinding.ImageItemBinding
import com.raineyi.imagesearchapp.domain.Image
import com.squareup.picasso.Picasso

class ImageAdapter : ListAdapter<Image, ImageViewHolder>(ImageDiffCallback) {

    var onLoadMoreListener: (() -> Unit)? = null
    var onImageClickListener: ((Image) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageItem = getItem(position)
        val binding = holder.binding

        Picasso.get()
                .load(imageItem.imageUrl)
                .into(binding.image)

        if(position>= currentList.size - 5) {
            onLoadMoreListener?.invoke()
        }

        binding.root.setOnClickListener {
            onImageClickListener?.invoke(imageItem)
        }
    }
}