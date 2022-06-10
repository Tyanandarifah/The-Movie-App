package com.example.api_service.usecase

import com.example.api_service.Constants
import com.example.api_service.paging.DiscoverMoviesPager
import com.example.api_service.service.DiscoverMovieService

class GetMoviesPagingUseCase(
    val discoverMovieService: DiscoverMovieService
) {
    operator fun invoke(args: String) =
        DiscoverMoviesPager.createPager(
            Constants.DEFAULT_PAGE_SIZE,
            discoverMovieService,
            args).flow
}