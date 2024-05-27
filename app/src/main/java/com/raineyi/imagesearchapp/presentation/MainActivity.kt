package com.raineyi.imagesearchapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.raineyi.imagesearchapp.BuildConfig
import com.raineyi.imagesearchapp.R
import com.raineyi.imagesearchapp.presentation.viewmodels.ImageViewModel
import com.raineyi.imagesearchapp.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainActivity @Inject constructor() : AppCompatActivity() {

    private val component by lazy {
        (application as ImageApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory)[ImageViewModel::class.java]
        launchFragmentImageList()
        checkImageDetailsFragment()
    }

    private fun checkImageDetailsFragment() {
        val imageDetailsFragment =
            supportFragmentManager.findFragmentByTag(ImageDetailsFragment.FRAGMENT_TAG)
        if (imageDetailsFragment != null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.images_container, imageDetailsFragment)
            fragmentTransaction.commit()
        }
    }

    private fun launchFragmentImageList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.images_container, ImageListFragment.newInstance())
            .commit()
    }
}