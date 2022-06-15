package id.indocyber.api_service.usecase

import id.indocyber.api_service.paging.DiscoverMoviesPager
import id.indocyber.api_service.service.DiscoverMovieService

class GetMoviesPagingUseCase(
    val discoverMovieService: DiscoverMovieService
) {
    operator fun invoke(args: String) =
        DiscoverMoviesPager.createPager(
            id.indocyber.api_service.Constants.DEFAULT_PAGE_SIZE,
            discoverMovieService,
            args).flow
}