package com.raineyi.imagesearchapp.di

import com.raineyi.imagesearchapp.data.network.ApiFactory
import com.raineyi.imagesearchapp.data.network.ApiService
import com.raineyi.imagesearchapp.data.repository.ImageRepositoryImpl
import com.raineyi.imagesearchapp.domain.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}