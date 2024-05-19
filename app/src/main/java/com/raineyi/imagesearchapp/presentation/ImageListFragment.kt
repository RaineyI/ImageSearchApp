package com.raineyi.imagesearchapp.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.raineyi.imagesearchapp.databinding.FragmentImageListBinding
import com.raineyi.imagesearchapp.di.ApplicationComponent
import com.raineyi.imagesearchapp.presentation.adapters.ImageAdapter
import com.raineyi.imagesearchapp.presentation.viewmodels.ImageViewModel
import com.raineyi.imagesearchapp.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ImageListFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentImageListBinding? = null
    private val binding: FragmentImageListBinding
        get() = _binding ?: throw RuntimeException("FragmentImageListBinding == null")

    private val component by lazy {
        (requireActivity().application as ImageApp).component
    }

    private lateinit var imageAdapter: ImageAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ImageViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeIsLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeIsLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupClickListener() {
        imageAdapter.onImageClickListener = {image ->

        }
    }

    private fun setupRecyclerView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(request: String?): Boolean {
                Log.d("TEST_API", "onQueryTextSubmit: $request")
                request?.let {
                    val imageAdapter = ImageAdapter()
//                    imageAdapter.onLoadMoreListener = {
//                        viewModel.loadImages(it)
//                    }

                    with(binding.rvImageList) {
                        layoutManager = GridLayoutManager(requireContext(), 2)
                        adapter = imageAdapter
                    }
                    viewModel.loadImages(request)
                    viewModel.listOfImages.observe(viewLifecycleOwner) { images ->
                        imageAdapter.submitList(images)
                    }
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
        setupClickListener()
    }

    companion object {
        fun newInstance(): ImageListFragment {
            return ImageListFragment()
        }
    }
}