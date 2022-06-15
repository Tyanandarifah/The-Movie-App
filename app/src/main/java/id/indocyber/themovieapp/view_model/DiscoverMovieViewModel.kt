package id.indocyber.themovieapp.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.GetMoviesPagingUseCase
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.discover_movie.DiscoverMovie
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverMovieViewModel @Inject constructor(
    application: Application,
    private val getMoviesPagingUseCase: GetMoviesPagingUseCase
) : BaseViewModel(application) {

    val moviesData = MutableLiveData<PagingData<DiscoverMovie>>()

    fun discoverMoviesByGenre(genres: String){
        viewModelScope.launch {
            getMoviesPagingUseCase.invoke(genres).collect{
                moviesData.postValue(it)
            }
        }
    }
}