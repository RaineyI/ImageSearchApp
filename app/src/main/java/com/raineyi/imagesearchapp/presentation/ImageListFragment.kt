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
import com.raineyi.imagesearchapp.R
import com.raineyi.imagesearchapp.databinding.FragmentImageListBinding
import com.raineyi.imagesearchapp.presentation.adapters.ImageListAdapter
import com.raineyi.imagesearchapp.presentation.viewmodels.ImageViewModel
import javax.inject.Inject

class ImageListFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentImageListBinding? = null
    private val binding: FragmentImageListBinding
        get() = _binding ?: throw RuntimeException("FragmentImageListBinding == null")


    private lateinit var imageListAdapter: ImageListAdapter

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ImageViewModel::class.java]
    }

    override fun onAttach(context: Context) {
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

    private fun setupRecyclerView() {

        imageListAdapter = ImageListAdapter()

        with(binding.rvImageList) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = imageListAdapter
        }

        Log.d("TEST_APP", "List VM: ${viewModel.hashCode()}")
        viewModel.listOfImages.observe(viewLifecycleOwner) { images ->
            Log.d("TEST_APP", "$images")
            imageListAdapter.submitList(images)
        }

        imageListAdapter.onImageClickListener = { image ->
            Log.d("TEST_APP", "onQueryTextSubmit: $image")

            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.images_container,
                    ImageDetailsFragment.newInstance(image)
                )
                .addToBackStack(null)
                .commit()
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(request: String?): Boolean {
                Log.d("TEST_APP", "onQueryTextSubmit: $request")
                request?.let {
                    viewModel.loadImages(it, NEW_SEARCH)
//                    imageAdapter.onLoadMoreListener = {
//                        viewModel.loadImages(it, CURRENT_SEARCH)
//                    }
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    companion object {

        private const val NEW_SEARCH = true
        private const val CURRENT_SEARCH = false
        fun newInstance(): ImageListFragment {
            return ImageListFragment()
        }
    }
}