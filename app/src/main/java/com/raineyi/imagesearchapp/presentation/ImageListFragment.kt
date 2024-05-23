package com.raineyi.imagesearchapp.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.raineyi.imagesearchapp.domain.Image
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

    private var currentQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeIsLoading()
        setupRecyclerView()
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

        imageListAdapter.onLoadMoreListener = {
            if (currentQuery != "") {
                viewModel.loadImages(currentQuery, CURRENT_SEARCH)
            }
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(request: String?): Boolean {
                if (isNetworkAvailable(requireContext())) {
                    request?.let {
                        currentQuery = it
                        viewModel.loadImages(it, NEW_SEARCH)
                    }
                } else {
                    imageListAdapter.submitList(null)
                    showError()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        viewModel.listOfImages.observe(viewLifecycleOwner) { images ->
            if (isNetworkAvailable(requireContext())) {
                hideError()
                imageListAdapter.submitList(images)
            }
        }

        imageListAdapter.onImageClickListener = { image ->
            launchDetailsFragment(image)
        }
    }

    private fun launchDetailsFragment(image: Image) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.images_container,
                ImageDetailsFragment.newInstance(image),
                ImageDetailsFragment.FRAGMENT_TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    private fun hideError() {
        binding.imError.visibility = View.GONE
        binding.tvError.visibility = View.GONE
    }

    private fun showError() {
        binding.imError.visibility = View.VISIBLE
        binding.tvError.visibility = View.VISIBLE
    }


    companion object {

        private const val NEW_SEARCH = true
        private const val CURRENT_SEARCH = false
        fun newInstance(): ImageListFragment {
            return ImageListFragment()
        }
    }
}