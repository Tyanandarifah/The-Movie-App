package com.example.themovieapp.fragment.movie_details

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.example.common.base.BaseFragment
import com.example.themovieapp.R
import com.example.themovieapp.databinding.MovieDetailFragmentLayoutBinding
import com.example.themovieapp.view_model.MovieDetailsViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YoutubePlayerSupportFragmentX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, MovieDetailFragmentLayoutBinding>() {
    override val vm: MovieDetailsViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.movie_detail_fragment_layout
    val adapter = MovieReviewPagingAdapter()
    val movieDetailsArgs: MovieDetailsFragmentArgs by navArgs()
    val loadStateAdapter = MovieReviewPagingStateAdapter()

    override fun initBinding(binding: MovieDetailFragmentLayoutBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        binding.recycler.adapter = adapter
        vm.getMovieDetails(movieDetailsArgs.movie)
        vm.getMovieVideo(movieDetailsArgs.movie)
        vm.movieDetailsData.observe(this) {
            binding.data = it.data
        }
        vm.movieReviewsPagingData.observe(this){
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(it)
            }

        }

        vm.videoData.observe(this) {
            val youtubeFragment = YoutubePlayerSupportFragmentX.newInstance()
            with(parentFragmentManager) {
                beginTransaction().apply {
                    add(R.id.video_trailer, youtubeFragment)
                    commit()
                }
            }

            youtubeFragment.initialize(
                "AIzaSyCPx1H6RyEmr1smab8pkQduF8v5k85A4vg",
                object : YoutubePlayerSupportFragmentX.OnInitializedListener(){
                    override fun onInitializationSuccess(
                        p0 : YouTubePlayer.Provider?,
                        p1: YouTubePlayer?,
                        p2 : Boolean
                    ){
                        p1?.cueVideo(it)
                    }

                    override fun onInitializationFailure(
                        p0 : YouTubePlayer.Provider?,
                        p1 : YouTubeInitializationResult?
                    ) {
                        Log.e("YoutubePlayer", "error ${p1?.name}")
                    }
                }
            )

        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.retryButton.visibility = View.VISIBLE
                binding.recycler.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.retryButton.setOnClickListener {
                    vm.getMovieDetails(movieDetailsArgs.movie)
                    vm.getMovieVideo(movieDetailsArgs.movie)
                }
            } else if (it.refresh is LoadState.Loading && adapter.itemCount == 0) {
                binding.retryButton.visibility = View.GONE
                binding.recycler.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE

            } else if (it.refresh is LoadState.NotLoading) {
                binding.retryButton.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }
        }
    }

}