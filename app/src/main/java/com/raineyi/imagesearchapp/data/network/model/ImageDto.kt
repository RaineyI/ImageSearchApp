package com.raineyi.imagesearchapp.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ImageDto(
    val id: String = generateUniqueIDUsingUUID(),

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("link")
    val link: String? = null,
) {
    companion object {
        fun generateUniqueIDUsingUUID(): String {
            return UUID.randomUUID().toString()
        }
    }
}