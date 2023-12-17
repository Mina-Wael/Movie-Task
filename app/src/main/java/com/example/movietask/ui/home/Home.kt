package com.example.movietask.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietask.databinding.FragmentHomeBinding
import com.example.movietask.domain.pojo.Movie
import com.example.movietask.utils.Common.showSnackBar
import com.example.movietask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _recyclerAdapter: MainRecyclerAdapter? = null
    private val recyclerAdapter: MainRecyclerAdapter get() = _recyclerAdapter!!

    private var _searchAdapter: MainRecyclerAdapter? = null
    private val searchAdapter get() = _searchAdapter!!

    val viewModel: HomeViewModel by viewModels()

    var isTextNull = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setupRecyclerView()
        setupSearch()
        setUpSearchRecycler()
        clearSearchView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listenToMovieStateFlow()
        startListenToSearchResult()
        setListenerToSearchView()


    }

    private fun setupRecyclerView() {

        _recyclerAdapter = MainRecyclerAdapter(MovieDiffUtil, onItemClick = onItemClick)
        binding.mainRecycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.search(newText)

                } else {
                    searchAdapter.setList(emptyList())
                }
                isTextNull = newText.isNullOrEmpty()
                return true
            }
        })
    }

    private fun startListenToSearchResult() {
        viewModel.searchLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    searchAdapter.setList(it.data)
                }

                is Resource.EmptyOrNUll -> {
                    binding.progress.visibility = View.GONE
                    showSnackBar(binding.root, it.message)
                }

                is Resource.Failed -> {
                    binding.progress.visibility = View.GONE
                    showSnackBar(binding.root, it.message)
                }
            }
        }

    }

    private fun setListenerToSearchView() {
        binding.searchView.setOnQueryTextFocusChangeListener { p0, p1 ->
            if (p1) {

                binding.searchRecycler.visibility = View.VISIBLE
                binding.mainRecycler.visibility = View.GONE
            } else {
                if (isTextNull) {
                    binding.searchRecycler.visibility = View.GONE
                    binding.mainRecycler.visibility = View.VISIBLE
                    searchAdapter.setList(emptyList())
                }
            }
        }
    }

    private fun setUpSearchRecycler() {
        _searchAdapter = MainRecyclerAdapter(MovieDiffUtil, onItemClick = onItemClick)
        binding.searchRecycler.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun listenToMovieStateFlow() {

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.movieStateFlow.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            binding.progress.visibility = View.GONE
                            recyclerAdapter.setList(it.data)
                        }

                        is Resource.Failed -> {
                            binding.progress.visibility = View.GONE
                            showSnackBar(binding.root, it.message)
                        }

                        is Resource.EmptyOrNUll -> {
                            binding.tvMessage.visibility = View.VISIBLE
                            binding.tvMessage.text = it.message
                            binding.progress.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private val onItemClick = fun(movie: Movie) {
        findNavController().navigate(HomeDirections.actionHomeToDetails(movie))
        clearSearchView()
    }

    private fun clearSearchView() {
        binding.searchView.setQuery("", false);
        binding.searchView.clearFocus();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}