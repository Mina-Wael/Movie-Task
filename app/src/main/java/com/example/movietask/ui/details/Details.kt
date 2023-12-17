package com.example.movietask.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movietask.databinding.FragmentDetailsBinding
import com.example.movietask.domain.pojo.Movie
import com.example.movietask.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Details : Fragment() {



    private val args: DetailsArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataToUi(args.movie!!)
    }

    private fun setDataToUi(movie: Movie) {
        Glide.with(requireContext()).load(Constants.IMAGE_BASE_URL + movie.poster_path)
            .into(binding.movieImage)
        binding.movieDescription.text = movie.overview
        binding.tvRate.text = movie.vote_average.toString()
        binding.tvDate.text = movie.release_date

    }


}