package com.example.api_service.service

import com.example.api_service.Constants
import com.example.common.entity.video.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieVideoService {
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : Response<VideoResponse>
}