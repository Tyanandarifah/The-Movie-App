package com.example.api_service.usecase

import com.example.api_service.service.MovieDetailsService
import com.example.common.base.BaseResponse
import com.example.common.entity.movie_details.MovieDetailsResponse
import kotlinx.coroutines.flow.flow

class GetMovieDetailsUseCase(
    private val movieDetailsService: MovieDetailsService
) {
    operator fun invoke(movieId: Int) = flow {
        try {
            emit(BaseResponse.loading())
            val data = movieDetailsService.getMovieDetails(movieId)
            if (data.isSuccessful) {
                data.body()?.let {
                    emit(BaseResponse.success(it))
                } ?: run {
                    emit(BaseResponse.failureBackend<MovieDetailsResponse>(
                        null
                    ))
                }
            }
        } catch (e: Exception) {
            emit(BaseResponse.failure(e))
        }
    }
}