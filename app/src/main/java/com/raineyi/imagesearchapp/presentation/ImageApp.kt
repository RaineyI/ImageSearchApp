package com.raineyi.imagesearchapp.presentation

import android.app.Application
import com.raineyi.imagesearchapp.di.DaggerApplicationComponent

class ImageApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}