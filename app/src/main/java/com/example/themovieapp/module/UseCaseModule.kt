package com.example.themovieapp.module

import com.example.api_service.service.*
import com.example.api_service.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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