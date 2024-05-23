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


//OkHttpClient client = new OkHttpClient().newBuilder()
// .build();
//MediaType mediaType = MediaType.parse("application/json");
//RequestBody body = RequestBody.create(mediaType, "{\"q\":\"apple\"}");
//Request request = new Request.Builder()
// .url("https://google.serper.dev/images")
// .method("POST", body)
// .addHeader("X-API-KEY", "35e03c08b35513efb1ba996f39a9e4c7051dc391")
// .addHeader("Content-Type", "application/json")
// .build();
//Response response = client.newCall(request).execute();




//@GET("films/top")
//@Headers(
//    "X-API-KEY: $API_KEY",
//    "Content-Type: application/json"
//)
//suspend fun getMovieResponse(
//    @Query(QUERY_PARAM_TYPE) type: String = "TOP_100_POPULAR_FILMS",
//    @Query(QUERY_PARAM_PAGE) page: Int = 1
//): MovieResponseDto
//
//@GET("films/{movieId}")
//@Headers(
//    "X-API-KEY: $API_KEY",
//    "Content-Type: application/json"
//)
//suspend fun getDescription(
//    @Path(QUERY_PARAM_MOVIE_ID) movieId: Int
//): DescriptionDto
//
//companion object {
//    private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
//    private const val QUERY_PARAM_TYPE = "type"
//    private const val QUERY_PARAM_PAGE = "page"
//    private const val QUERY_PARAM_MOVIE_ID = "movieId"
//}