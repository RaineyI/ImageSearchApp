package com.raineyi.imagesearchapp.data.mapper

import com.raineyi.imagesearchapp.data.network.model.ImageDto
import com.raineyi.imagesearchapp.domain.Image
import javax.inject.Inject

class ImageMapper @Inject constructor() {

    fun mapDtoToImage(dto: ImageDto): Image {
        return Image(
            id = dto.id,
            title = dto.title,
            imageUrl = dto.imageUrl,
            link = dto.link
        )
    }
}