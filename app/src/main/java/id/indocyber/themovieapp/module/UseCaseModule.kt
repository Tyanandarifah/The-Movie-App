package id.indocyber.themovieapp.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.*
import id.indocyber.api_service.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetGenresUseCase(genreService: GenreService) = GetGenresUseCase(genreService)

    @Provides
    fun provideGetMoviesPagingUseCase(discoverMovieService: DiscoverMovieService) =
        GetMoviesPagingUseCase(discoverMovieService)

    @Provides
    fun provideGetMovieDetailsUseCase(movieDetailsService: MovieDetailsService) =
        GetMovieDetailsUseCase(movieDetailsService)

    @Provides
    fun provideMovieReviewPagingUseCase(movieReviewService: MovieReviewService) =
        MovieReviewPagingUseCase(movieReviewService)

    @Provides
    fun provideGetMovieVideoUseCase(movieVideoService: MovieVideoService) =
        GetMovieVideoUseCase(movieVideoService)
}