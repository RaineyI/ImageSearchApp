package com.raineyi.imagesearchapp.data.network

import com.raineyi.imagesearchapp.data.network.model.ImageResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("/images")
    @Headers(
        "X-API-KEY: $API_KEY",
        "Content-Type: application/json"
    )
    suspend fun getImageResponse(
        @Query(QUERY_PARAM_REQUEST) request: String,
        @Query(QUERY_PARAM_PAGE) page: Int
    ): ImageResponseDto

    companion object {
        private const val API_KEY = "35e03c08b35513efb1ba996f39a9e4c7051dc391"
        private const val QUERY_PARAM_REQUEST = "q"
        private const val QUERY_PARAM_PAGE = "page"
    }
}