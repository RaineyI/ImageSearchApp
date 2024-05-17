package com.raineyi.imagesearchapp.data.repository

import android.util.Log
import com.raineyi.imagesearchapp.data.mapper.ImageMapper
import com.raineyi.imagesearchapp.data.network.ApiService
import com.raineyi.imagesearchapp.domain.Image
import com.raineyi.imagesearchapp.domain.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val mapper: ImageMapper,
    private val apiService: ApiService
) : ImageRepository {
    override suspend fun loadImages(request: String, page: Int): List<Image>? {
        return try {
            val imageResponseDto = apiService.getImageResponse(request = request, page = page)
            imageResponseDto.imageList?.map { mapper.mapDtoToImage(it) }
        } catch (e: Exception) {
            Log.d("TEST_API", e.message.toString())
            null
        }
    }
}