package com.raineyi.imagesearchapp.domain

data class Image (
    val id: Int,
    val title: String? = null,
    val imageUrl: String? = null,
    val link: String? = null,
)