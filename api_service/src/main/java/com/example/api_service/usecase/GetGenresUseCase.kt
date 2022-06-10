package com.example.api_service.usecase

import com.example.api_service.service.GenreService
import com.example.common.base.BaseResponse
import com.example.common.entity.genre.Genre
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class GetGenresUseCase(val genreService: GenreService) {
    operator fun invoke() = flow<BaseResponse<List<Genre>>> {
        try {
            emit(BaseResponse.loading())
            val result = genreService.getGenres()
            if (result.isSuccessful) {
                result.body()?.let {
                    emit(BaseResponse.success(it.genres))
                }
            }
        } catch (e: Exception) {
            emit(BaseResponse.failure(e))

        }
    }
}