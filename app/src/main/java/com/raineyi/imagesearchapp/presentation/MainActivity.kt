package com.raineyi.imagesearchapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raineyi.imagesearchapp.R
import com.raineyi.imagesearchapp.data.network.ApiFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = ApiFactory.apiService
        lifecycleScope.launch {
            val imageList = apiService.getImageResponse()
            Log.d("TEST_API", "$imageList")
        }


    }
}