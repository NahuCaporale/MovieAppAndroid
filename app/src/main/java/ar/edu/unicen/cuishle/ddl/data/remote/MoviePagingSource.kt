package ar.edu.unicen.cuishle.ddl.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieDTO
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieResponseDTO
import okio.IOException
import retrofit2.Response

class MoviePagingSource(private val api: MovieApi, private val query: String? = null) :
    PagingSource<Int, MovieDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDTO> {
        val page = params.key ?: 1

        return try {

            val response = if (!query.isNullOrBlank()) {
                api.getMovieBySearch(query = query, page = page)
            } else {
                api.getPopularMovies(page = page)
            }
            var movies = response.body()

            if (response.isSuccessful && movies != null) {
                LoadResult.Page(
                    data = movies.results,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page >= movies.totalPages) null else page + 1
                )
            } else {
                LoadResult.Error(IOException("Error ${response.code()} to load movies"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
