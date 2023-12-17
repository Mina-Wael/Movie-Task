package com.example.movietask.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietask.R
import com.example.movietask.databinding.FragmentHomeBinding
import com.example.movietask.domain.pojo.ResultPojo
import com.example.movietask.utils.Common.showSnackBar
import com.example.movietask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _recyclerAdapter: MainRecyclerAdapter? = null
    private val recyclerAdapter: MainRecyclerAdapter get() = _recyclerAdapter!!

    val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listenToMovieStateFlow()


    }

    private fun setupRecyclerView() {

        _recyclerAdapter = MainRecyclerAdapter(onItemClick = onItemClick)
        binding.mainRecycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun listenToMovieStateFlow() {

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

    private val onItemClick = fun(movie: ResultPojo) {
        findNavController().navigate(HomeDirections.actionHomeToDetails(movie))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}