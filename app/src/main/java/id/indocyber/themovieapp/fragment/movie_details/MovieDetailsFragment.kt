package id.indocyber.themovieapp.fragment.movie_details

import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment
import id.indocyber.themovieapp.R
import id.indocyber.themovieapp.databinding.MovieDetailFragmentLayoutBinding
import id.indocyber.themovieapp.view_model.MovieDetailsViewModel
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
        lifecycle.addObserver(binding.videoTrailer)
        vm.videoData.observe(this) {
            binding.videoTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    val videoId = it
                    youTubePlayer.loadVideo(videoId, 0F)
                }
            })
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error && adapter.itemCount == 0) {
                binding.retryButton.visibility = View.VISIBLE
                binding.recycler.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.retryButton.setOnClickListener {
                    adapter.retry()
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