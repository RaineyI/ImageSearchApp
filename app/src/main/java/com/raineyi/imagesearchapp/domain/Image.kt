package com.raineyi.imagesearchapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: String,
    val title: String? = null,
    val imageUrl: String? = null,
    val link: String? = null,
) : Parcelable