package com.raineyi.imagesearchapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ImageResponseDto(

    @SerializedName("images")
    val imageList: List<ImageDto>? = null
)