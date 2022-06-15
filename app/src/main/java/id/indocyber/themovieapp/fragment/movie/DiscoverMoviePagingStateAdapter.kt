package id.indocyber.themovieapp.fragment.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.themovieapp.databinding.MoviesStateLayoutBinding

class DiscoverMoviePagingStateAdapter() :
    LoadStateAdapter<DiscoverMovieStateViewHolder>() {

    override fun onBindViewHolder(holder: DiscoverMovieStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DiscoverMovieStateViewHolder =
        DiscoverMovieStateViewHolder(
            MoviesStateLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        ).apply {
            this.bind(loadState)
        }
}
class DiscoverMovieStateViewHolder(
    private val binding: MoviesStateLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.Error -> {
                binding.retryButton.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            is LoadState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
            }
            is LoadState.NotLoading -> {
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
            }
        }
    }
}