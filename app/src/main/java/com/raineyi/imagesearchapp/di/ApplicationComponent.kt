package com.raineyi.imagesearchapp.di

import android.app.Application
import com.raineyi.imagesearchapp.presentation.ImageDetailsFragment
import com.raineyi.imagesearchapp.presentation.ImageListFragment
import com.raineyi.imagesearchapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: ImageListFragment)
    fun inject(fragment: ImageDetailsFragment)
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
