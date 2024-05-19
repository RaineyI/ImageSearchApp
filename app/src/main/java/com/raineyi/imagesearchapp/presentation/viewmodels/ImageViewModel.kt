package com.raineyi.imagesearchapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineyi.imagesearchapp.domain.Image
import com.raineyi.imagesearchapp.domain.LoadImageListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageViewModel @Inject constructor(
    private val loadImageListUseCase: LoadImageListUseCase
) : ViewModel() {

    private var page = 1

    private var _listOfImages = MutableLiveData<List<Image>>()
    val listOfImages: LiveData<List<Image>>
        get() = _listOfImages

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loadImages(request: String) {
        val loading = isLoading.value
        if (loading != null && loading) {
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val images = loadImageListUseCase(request, page)
                val loadedImages = _listOfImages.value?.toMutableList()
                if (loadedImages != null) {
                    images?.let {
                        loadedImages.addAll(it)
                        _listOfImages.value = it
                    }
                } else {
                    images?.let {
                        _listOfImages.value = it
                    }
                }
                Log.d("TEST_API", "Loaded page: $page")
                page++
            } catch (e: Exception) {
                Log.d("TEST_API", e.message.toString())
            } finally {
            }
            _isLoading.value = false
        }
    }
}