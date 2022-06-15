package id.indocyber.themovieapp.fragment.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.discover_movie.DiscoverMovie
import id.indocyber.themovieapp.databinding.MoviesItemLayoutBinding

class DiscoverMoviePagingAdapter(
    val isSelected: (Int) -> Unit
) : PagingDataAdapter<DiscoverMovie, DiscoverViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.data = data
        holder.binding.root.setOnClickListener {
            data?.let { it ->
                isSelected(it.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        return DiscoverViewHolder(
            MoviesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DiscoverMovie>() {
            override fun areItemsTheSame(oldItem: DiscoverMovie, newItem: DiscoverMovie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DiscoverMovie, newItem: DiscoverMovie): Boolean {
                return true
            }

        }
    }
}
class DiscoverViewHolder(
    val binding: MoviesItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root){

}