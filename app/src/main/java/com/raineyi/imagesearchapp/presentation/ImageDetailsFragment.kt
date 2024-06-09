package com.raineyi.imagesearchapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.raineyi.imagesearchapp.R
import com.raineyi.imagesearchapp.databinding.FragmentImageDetailsBinding
import com.raineyi.imagesearchapp.domain.Image
import com.raineyi.imagesearchapp.presentation.adapters.ImageDetailViewPagerAdapter
import com.raineyi.imagesearchapp.presentation.adapters.ZoomOutPageTransformer
import com.raineyi.imagesearchapp.presentation.viewmodels.ImageViewModel
import javax.inject.Inject

class ImageDetailsFragment @Inject constructor() : Fragment(R.layout.fragment_image_details) {

    private val binding by viewBinding(FragmentImageDetailsBinding::bind)

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ImageViewModel::class.java]
    }
    private lateinit var image: Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager2()
        setUpOnBackPressedArrow()
        setUpLinkButton()
    }

    private fun setUpLinkButton() {
        binding.buttonLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(image.link)
            startActivity(intent)
        }
    }

    private fun setUpOnBackPressedArrow() {
        binding.arrowBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun parseParam() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_IMAGE)) {
            throw RuntimeException("Param image is absent")
        }
        when {
            SDK_INT >= 33 -> args.getParcelable(
                EXTRA_IMAGE,
                Image::class.java
            )

            else -> args.getParcelable<Image>(EXTRA_IMAGE)
        }?.let {
            image = it
        }
    }

    private fun setUpViewPager2() {
        val viewPagerAdapter = ImageDetailViewPagerAdapter()

        viewModel.listOfImages.observe(viewLifecycleOwner) { images ->
            viewPagerAdapter.submitList(images)
            with(binding.imageViewPager) {
                adapter = viewPagerAdapter

                setCurrentItem(images.indexOf(image), false)
                setPageTransformer(ZoomOutPageTransformer())
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        image = images[position]
                    }
                })
            }
        }
    }

    companion object {
        private const val EXTRA_IMAGE = "extra_image"
        const val FRAGMENT_TAG = "image_details_tag"
        fun newInstance(image: Image): ImageDetailsFragment {
            return ImageDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_IMAGE, image)
                }
            }
        }
    }
}