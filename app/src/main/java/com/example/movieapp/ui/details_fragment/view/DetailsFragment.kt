package com.example.movieapp.ui.details_fragment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.data.model.MoviesDetailsResponse
import com.example.movieapp.databinding.DetailsFragmentBinding
import com.example.movieapp.ui.details_fragment.viewmodel.DetailsViewModel
import com.example.movieapp.ui.details_fragment.adapter.GenresAdapter
import com.example.movieapp.utils.Status
import com.example.movieapp.utils.createImageUrl
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val safeArgs: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DetailsFragmentBinding.inflate(inflater, container, false)

        viewModel.getMovieDetails(safeArgs.movieId).observe(viewLifecycleOwner) { it ->
            binding.loading.visibility = View.GONE
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        setUpView(it)
                    }
                }
                Status.ERROR -> {
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "an error occur reason :\n${it.message}"
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }

            }

        }
        viewModel.getMovieDetails(safeArgs.movieId)
        return binding.root

    }

    private fun setUpView(movie: MoviesDetailsResponse) {

        binding.tvMovieTitle.text = movie.title
        binding.tvMovieReleaseDate.text = movie.release_date.plus(" . ${movie.runtime} mins")
        binding.tvRate.text = movie.vote_average.toString()
        binding.tvRateCount.text = movie.vote_count.toString()
        binding.tvAboutMovie.text = movie.overview

        val adapter = GenresAdapter(movie.genres)
        binding.rvGenres.adapter = adapter

        binding.tvOriginalTitle.text = movie.original_title
        binding.tvReleaseDate.text = movie.release_date
        binding.tvBudget.text = numberFormatter(movie.budget).plus(" USD")
        binding.tvRevenue.text = numberFormatter(movie.revenue).plus(" USD")
        binding.tvRuntime.text = movie.runtime.toString().plus(" mins")
        binding.tvVote.text = movie.vote_average.toString()
        binding.tvVoteCount.text = movie.vote_count.toString()
        binding.tvProductionCompanies.text = movie.production_companies.toString()

        Glide.with(this)
            .load(createImageUrl(movie.poster_path))
            .into(binding.ivPoster)
        Glide.with(this)
            .load(createImageUrl(movie.poster_path))
            .into(binding.ivMovieLogo)

    }

    private fun numberFormatter(number: Any): String {
        return NumberFormat.getInstance().format(number)
    }

}