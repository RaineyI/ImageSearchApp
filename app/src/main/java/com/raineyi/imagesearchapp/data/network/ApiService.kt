package com.raineyi.imagesearchapp.data.network

import com.raineyi.imagesearchapp.BuildConfig
import com.raineyi.imagesearchapp.data.network.model.ImageResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {


    @GET("/images")
    @Headers(
        "Content-Type: application/json"
    )
    suspend fun getImageResponse(
        @Query(QUERY_PARAM_REQUEST) request: String,
        @Query(QUERY_PARAM_PAGE) page: Int
    ): ImageResponseDto

    companion object {
        private const val QUERY_PARAM_REQUEST = "q"
        private const val QUERY_PARAM_PAGE = "page"
    }
}