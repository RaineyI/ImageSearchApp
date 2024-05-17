package com.raineyi.imagesearchapp.domain

interface ImageRepository {
    suspend fun loadImages(request: String, page: Int): List<Image>?
}