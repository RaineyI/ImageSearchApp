package com.raineyi.imagesearchapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.raineyi.imagesearchapp.databinding.ImageItemDetailsBinding
import com.raineyi.imagesearchapp.domain.Image
import com.squareup.picasso.Picasso


class ImageDetailViewPagerAdapter : ListAdapter<Image, ImageDetailViewHolder>(ImageDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDetailViewHolder {
        val binding = ImageItemDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageDetailViewHolder, position: Int) {
        val imageItem = getItem(position)
        Picasso.get()
            .load(imageItem.imageUrl)
            .into(holder.binding.imageView)
    }
}