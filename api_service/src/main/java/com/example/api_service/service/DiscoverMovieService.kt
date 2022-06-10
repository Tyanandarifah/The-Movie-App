package com.example.api_service.service

import android.provider.SyncStateContract
import com.example.api_service.Constants
import com.example.common.entity.discover_movie.DiscoverMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMovieService {
    @GET("discover/movie")
    suspend fun getMovieByGenre(
        @Query("with_genres") genres: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : Response<DiscoverMovieResponse>
}