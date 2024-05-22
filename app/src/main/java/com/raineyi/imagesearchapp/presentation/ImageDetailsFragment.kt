package com.raineyi.imagesearchapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.raineyi.imagesearchapp.databinding.FragmentImageDetailsBinding
import com.raineyi.imagesearchapp.domain.Image
import com.raineyi.imagesearchapp.presentation.adapters.ImageDetailViewPagerAdapter
import com.raineyi.imagesearchapp.presentation.adapters.ZoomOutPageTransformer
import com.raineyi.imagesearchapp.presentation.viewmodels.ImageViewModel
import javax.inject.Inject

class ImageDetailsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentImageDetailsBinding? = null
    private val binding: FragmentImageDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentImageDetailsBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ImageViewModel::class.java]
    }
    private lateinit var image: Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
        Log.d("TEST_APP", "onCreate, param = $image")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager2()
        setUpOnBackPressedArrow()
        setUpLinkButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
        Log.d("TEST_APP", "Details VM: ${viewModel.hashCode()}")

        viewModel.listOfImages.observe(viewLifecycleOwner) { images ->
            Log.d("TEST_APP", "observe view model: $images")
            viewPagerAdapter.submitList(images)
            with(binding.imageViewPager) {
                adapter = viewPagerAdapter
                setCurrentItem(images.indexOf(image), false)
                setPageTransformer(ZoomOutPageTransformer())
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Log.d("TEST_APP", "Item: $position - ${images[position]}")
                        image = images[position]
                    }
                })
            }
        }
    }

    companion object {
        private const val EXTRA_IMAGE = "extra_image"
        fun newInstance(image: Image): ImageDetailsFragment {
            return ImageDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_IMAGE, image)
                }
            }
        }
    }
}