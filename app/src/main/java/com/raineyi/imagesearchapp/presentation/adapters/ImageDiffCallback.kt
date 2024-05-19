package com.raineyi.imagesearchapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.raineyi.imagesearchapp.domain.Image

object ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}