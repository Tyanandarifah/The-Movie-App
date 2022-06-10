package com.example.themovieapp.fragment.movie_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.themovieapp.databinding.MovieReviewStateLayoutBinding

class MovieReviewPagingStateAdapter() :
    LoadStateAdapter<MovieReviewPagingStateAdapter.MovieReviewStateViewHolder>() {
    class MovieReviewStateViewHolder(
        private val binding: MovieReviewStateLayoutBinding
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

    override fun onBindViewHolder(holder: MovieReviewStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieReviewStateViewHolder =
        MovieReviewStateViewHolder(
            MovieReviewStateLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        ).apply {
            this.bind(loadState)
        }
}