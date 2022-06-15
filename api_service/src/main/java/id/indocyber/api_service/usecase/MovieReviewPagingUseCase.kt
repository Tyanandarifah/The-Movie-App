package id.indocyber.api_service.usecase

import id.indocyber.api_service.paging.MovieReviewPager
import id.indocyber.api_service.service.MovieReviewService

class MovieReviewPagingUseCase(
    private val movieReviewService: MovieReviewService
) {
    operator fun invoke(movieId: Int) =
        MovieReviewPager.createPager(
            id.indocyber.api_service.Constants.DEFAULT_PAGE_SIZE,
            movieReviewService,
            movieId).flow
}