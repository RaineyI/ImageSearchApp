package com.raineyi.imagesearchapp.data.repository

import android.util.Log
import com.raineyi.imagesearchapp.data.network.ApiService
import com.raineyi.imagesearchapp.data.network.model.ImageDto
import com.raineyi.imagesearchapp.domain.Image
import com.raineyi.imagesearchapp.domain.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ImageRepository {
    override suspend fun loadImages(request: String, page: Int): List<Image>? {
        return try {
            val imageResponseDto = apiService.getImageResponse(request = request, page = page)
            imageResponseDto.imageList?.map { it.toImage() }
        } catch (e: Exception) {
            Log.d("TEST_API", e.message.toString())
            null
        }
    }

    private fun ImageDto.toImage(): Image {
        return Image(
            id = this.id,
            title = this.title,
            imageUrl = this.imageUrl,
            link = this.link
        )
    }
}