package id.indocyber.api_service.usecase

import id.indocyber.common.base.BaseResponse
import id.indocyber.api_service.service.MovieVideoService
import id.indocyber.common.entity.video.VideoResponse
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