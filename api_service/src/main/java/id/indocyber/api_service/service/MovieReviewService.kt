package id.indocyber.api_service.service

import id.indocyber.common.entity.movie_review.MovieReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieReviewService {
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = id.indocyber.api_service.Constants.API_KEY,
        @Query("page") page: Int
    ) : Response<MovieReviewResponse>
}