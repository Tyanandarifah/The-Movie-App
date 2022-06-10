package com.example.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.api_service.service.DiscoverMovieService
import com.example.common.entity.discover_movie.DiscoverMovie

class DiscoverMovieDataSource(
    private val discoverMovieService: DiscoverMovieService,
    private val genres: String
) : PagingSource<Int, DiscoverMovie>() {
    override fun getRefreshKey(state: PagingState<Int, DiscoverMovie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovie> {
        val page = params.key ?: 1
        val prevPage = if (page == 1) null else - 1

        try {
            // params.loadSize
            val data = discoverMovieService.getMovieByGenre(
                genres, page
            )

            if (data.isSuccessful) {
                data.body()?.let {
                    val nextPage = if (it.results.isEmpty()) null else page + 1
                    return LoadResult.Page(data = it.results, prevPage, nextPage)
                } ?: kotlin.run {
                    return LoadResult.Page(arrayListOf(), prevPage, null)
                }
            } else {
                return LoadResult.Error(Exception("Error Backend : ${data.code()}"))
            }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}

object DiscoverMoviesPager {
    fun createPager(
        pageSize: Int,
        discoverMovieService: DiscoverMovieService,
        genres: String
    ): Pager<Int, DiscoverMovie> = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = {
            DiscoverMovieDataSource(discoverMovieService, genres)
        }
    )
}