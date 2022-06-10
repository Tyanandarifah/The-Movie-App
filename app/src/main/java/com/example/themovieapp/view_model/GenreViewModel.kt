package com.example.themovieapp.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.SelectionTracker
import com.example.api_service.usecase.GetGenresUseCase
import com.example.common.base.BaseResponse
import com.example.common.base.BaseViewModel
import com.example.common.entity.genre.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    application: Application,
    val genresUseCase: GetGenresUseCase
) : BaseViewModel(application) {
    val genreDataState = MutableLiveData<BaseResponse<List<Genre>>>()
    var selection: SelectionTracker<Long>? = null

    init {
        viewModelScope.launch {
            genresUseCase().collect {
                genreDataState.postValue(it)
            }
        }
    }
}