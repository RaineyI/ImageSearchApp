package com.raineyi.imagesearchapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raineyi.imagesearchapp.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelFactory @Inject constructor(
    private val viewModelProviders:
    @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}


//@ApplicationScope
//class ViewModelFactory @Inject constructor(
//    private val viewModelProviders:
//    @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        val provider = viewModelProviders[modelClass]
//            ?: viewModelProviders.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
//            ?: throw IllegalArgumentException("Unknown ViewModel class $modelClass")
//        return provider.get() as T
//    }
//}

//@ApplicationScope
//class ViewModelFactory @Inject constructor(
//    private val viewModelProviders:
//    @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        // В вашем случае, при запросе SharedViewModel, верните одну и ту же инстанцию
//        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
//            return viewModelProviders[ImageViewModel::class.java]?.get() as T
//        }
//        return viewModelProviders[modelClass]?.get() as T
//    }
//}
