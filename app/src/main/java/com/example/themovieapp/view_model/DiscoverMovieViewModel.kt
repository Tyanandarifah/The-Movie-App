package com.example.themovieapp.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.recyclerview.selection.SelectionTracker
import com.example.api_service.usecase.GetMoviesPagingUseCase
import com.example.common.base.BaseViewModel
import com.example.common.entity.discover_movie.DiscoverMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverMovieViewModel @Inject constructor(
    application: Application,
    private val getMoviesPagingUseCase: GetMoviesPagingUseCase
) : BaseViewModel(application) {

    val moviesData = MutableLiveData<PagingData<DiscoverMovie>>()
    var selection = MutableLiveData<List<DiscoverMovie>>()

    fun discoverMoviesByGenre(genres: String){
        viewModelScope.launch {
            getMoviesPagingUseCase.invoke(genres).collect{
                moviesData.postValue(it)
            }
        }
    }
}