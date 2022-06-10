package com.example.api_service.usecase

import com.example.api_service.service.MovieVideoService
import com.example.common.base.BaseResponse
import com.example.common.entity.movie_details.MovieDetailsResponse
import com.example.common.entity.video.VideoResponse
import kotlinx.coroutines.flow.flow

class GetMovieVideoUseCase(
    private val movieVideoService: MovieVideoService
) {
    suspend operator fun invoke(movieId: Int) = flow {
        try {
            emit(BaseResponse.loading())
            val data = movieVideoService.getMovieVideo(movieId)
            if (data.isSuccessful) {
                data.body()?.let {
                    emit(BaseResponse.success(it))
                } ?: run {
                    emit(BaseResponse.failureBackend<VideoResponse>(
                        null
                    ))
                }
            }
        } catch (e: Exception) {
            emit(BaseResponse.failure(e))
        }
    }
}