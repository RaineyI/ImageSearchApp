package com.raineyi.imagesearchapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.raineyi.imagesearchapp.R
import com.raineyi.imagesearchapp.databinding.ActivityMainBinding
import com.raineyi.imagesearchapp.presentation.viewmodels.ImageViewModel
import com.raineyi.imagesearchapp.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as ImageApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ImageViewModel

    //    private val viewModel by lazy {
//        ViewModelProvider(this, viewModelFactory)[ImageViewModel::class.java]
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory)[ImageViewModel::class.java]
        launchFragmentImageList()
    }

    private fun launchFragmentImageList() {
//        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.images_container, ImageListFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}