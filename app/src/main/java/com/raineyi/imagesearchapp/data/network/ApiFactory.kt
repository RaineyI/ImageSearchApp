package com.raineyi.imagesearchapp.data.network

import com.raineyi.imagesearchapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val API_KEY = BuildConfig.IMAGE_API_KEY
    private const val BASE_URL = "https://google.serper.dev"

        private val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("X-API-KEY", API_KEY)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val apiService = retrofit.create(ApiService::class.java)
}
