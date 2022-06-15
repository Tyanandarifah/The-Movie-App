package id.indocyber.api_service.usecase

import id.indocyber.api_service.service.GenreService
import id.indocyber.common.base.BaseResponse
import id.indocyber.common.entity.genre.Genre
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