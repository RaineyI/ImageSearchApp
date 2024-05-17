package com.raineyi.imagesearchapp.di

import com.raineyi.imagesearchapp.presentation.viewmodels.ImagesViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<ImagesViewModel>)