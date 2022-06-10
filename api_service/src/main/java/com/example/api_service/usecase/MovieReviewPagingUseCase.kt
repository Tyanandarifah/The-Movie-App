package com.example.api_service.usecase

import com.example.api_service.Constants
import com.example.api_service.paging.MovieReviewPager
import com.example.api_service.service.MovieReviewService

class MovieReviewPagingUseCase(
    private val movieReviewService: MovieReviewService
) {
    operator fun invoke(movieId: Int) =
        MovieReviewPager.createPager(
            Constants.DEFAULT_PAGE_SIZE,
            movieReviewService,
            movieId).flow
}