package com.raineyi.imagesearchapp.di

import androidx.lifecycle.ViewModel
import com.raineyi.imagesearchapp.presentation.viewmodels.ImagesViewModel
import dagger.Binds

interface ViewModelModule {

    @Binds
    @ViewModelKey(ImagesViewModel::class)
    fun bindViewModel(viewModel: ImagesViewModel): ViewModel
}