package com.raineyi.imagesearchapp.di

import androidx.lifecycle.ViewModel
import com.raineyi.imagesearchapp.presentation.viewmodels.ImageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel::class)
    fun bindImageViewModel(viewModel: ImageViewModel): ViewModel
}