package com.raineyi.imagesearchapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raineyi.imagesearchapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchFragmentImageList()
    }

    private fun launchFragmentImageList(){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.images_container, ImageListFragment.newInstance())
            .commit()
    }
}