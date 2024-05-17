package com.raineyi.imagesearchapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ImageDto (
    @SerializedName("position")
    val id: Int,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("link")
    val link: String? = null,
)