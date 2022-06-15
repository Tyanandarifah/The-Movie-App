package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.MovieReviewService
import id.indocyber.common.entity.movie_review.MovieReview

class MovieReviewDataSource(
    private val movieReviewService: MovieReviewService,
    private val movieId: Int
) : PagingSource<Int, MovieReview>() {
    override fun getRefreshKey(state: PagingState<Int, MovieReview>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,MovieReview> {
        return try {
            val result = movieReviewService.getMovieReviews(
                movieId,
                page = params.key ?: 1
            )
            result.body()?.let {
                LoadResult.Page(data = it.results, if(it.page == 1) null else it.page - 1,
                    if(it.results.isEmpty()) null else it.page +1)
            } ?: LoadResult.Error(Exception("invalid data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
object MovieReviewPager {
    fun createPager(
        pageSize: Int,
        movieReviewService: MovieReviewService,
        movieId: Int
    ): Pager<Int, MovieReview> = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = {
            MovieReviewDataSource(movieReviewService, movieId)
        }
    )
}