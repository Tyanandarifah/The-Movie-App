package id.indocyber.themovieapp.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.usecase.GetMovieDetailsUseCase
import id.indocyber.api_service.usecase.GetMovieVideoUseCase
import id.indocyber.api_service.usecase.MovieReviewPagingUseCase
import id.indocyber.common.base.BaseResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movie_details.MovieDetailsResponse
import id.indocyber.common.entity.movie_review.MovieReview
import id.indocyber.common.ext.mutableLiveDataOf
import id.indocyber.common.ext.singleLiveEventOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    application: Application,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val movieReviewPagingUseCase: MovieReviewPagingUseCase,
    val getMovieVideoUseCase: GetMovieVideoUseCase
) : BaseViewModel(application) {
    val movieDetailsData = MutableLiveData<BaseResponse<MovieDetailsResponse>>()
    val movieReviewsPagingData = mutableLiveDataOf<PagingData<MovieReview>>()
    val videoData = singleLiveEventOf<String>()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase.invoke(movieId).collect {
                movieDetailsData.postValue(it)
            }
            movieReviewPagingUseCase(movieId).collect{
                movieReviewsPagingData.postValue(it)
            }
        }
    }

    fun getMovieVideo(videoId: Int) {
        viewModelScope.launch {
            getMovieVideoUseCase.invoke(videoId).collect{
                it.data?.results?.get(0)?.key?.let { it1 ->
                    videoData.postValue(it1)
                }
            }
        }
    }

}