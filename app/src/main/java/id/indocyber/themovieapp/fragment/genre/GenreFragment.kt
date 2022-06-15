package id.indocyber.themovieapp.fragment.genre

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import id.indocyber.themovieapp.R
import id.indocyber.themovieapp.databinding.GenreFragmentLayoutBinding
import id.indocyber.themovieapp.view_model.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.base.BaseFragment

@AndroidEntryPoint
class GenreFragment : BaseFragment<GenreViewModel, GenreFragmentLayoutBinding>() {
    override val vm: GenreViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.genre_fragment_layout
    val adapter = GenreAdapter() {
        vm.selection?.isSelected(it) ?: false
    }

    override fun initBinding(binding: GenreFragmentLayoutBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter
        vm.selection = vm.selection?.let {
            createTracker().apply {
                it.selection.forEach {
                    this.select(it)
                }
            }
        } ?: run {
            createTracker()
        }
        observeResponseData(vm.genreDataState, success = {
            binding.progressBar.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
            binding.fabToMovie.visibility = View.VISIBLE
            binding.fabToMovie.setOnClickListener {
                findNavController().navigate(
                    GenreFragmentDirections.toMovies(
                        vm.selection?.selection?.toMutableList().orEmpty().joinToString(",")
                    )
                )

            }
            adapter.data.submitList(it)
        }, error = {
            binding.recycler.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun createTracker()=
        SelectionTracker.Builder<Long>(
            this@GenreFragment::class.java.name,
            binding.recycler,
            adapter.getItemKeyProvider(),
            GenreItemDetailsLookUp(binding.recycler),
            StorageStrategy.createLongStorage()
        ).withOnItemActivatedListener { a, _ ->
            a.selectionKey?.let {
                vm.selection?.select(it)
            }
            true
        }.withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
}