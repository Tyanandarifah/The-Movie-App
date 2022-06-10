package com.example.themovieapp.fragment.movie

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.example.common.base.BaseFragment
import com.example.themovieapp.R
import com.example.themovieapp.databinding.MoviesFragmentLayoutBinding
import com.example.themovieapp.databinding.MoviesItemLayoutBinding
import com.example.themovieapp.view_model.DiscoverMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverMovieFragment : BaseFragment<DiscoverMovieViewModel, MoviesFragmentLayoutBinding>() {
    override val vm: DiscoverMovieViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.movies_fragment_layout
    val adapter = DiscoverMoviePagingAdapter() {
        findNavController().navigate(DiscoverMovieFragmentDirections.toMovieDetails(it))
    }
    val moviesArgs: DiscoverMovieFragmentArgs by navArgs()
    val loadStateAdapter = DiscoverMoviePagingStateAdapter()

    override fun initBinding(binding: MoviesFragmentLayoutBinding) {
        super.initBinding(binding)
        binding.recyclerMovie.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        vm.discoverMoviesByGenre(moviesArgs.genre)
        vm.moviesData.observe(this) {
            binding.progressBar.visibility = View.GONE
            binding.recyclerMovie.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.retryButton.visibility = View.VISIBLE
                binding.recyclerMovie.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.retryButton.setOnClickListener {
                    vm.discoverMoviesByGenre(moviesArgs.genre)
                }
            } else if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.retryButton.visibility = View.GONE
                binding.recyclerMovie.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE

            } else if (it.refresh is LoadState.NotLoading) {
                binding.retryButton.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.recyclerMovie.visibility = View.VISIBLE
            }
        }
    }

}