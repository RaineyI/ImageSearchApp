package com.raineyi.imagesearchapp.domain

import javax.inject.Inject

class LoadImageListUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(request: String, page: Int) = repository.loadImages(request, page)
}