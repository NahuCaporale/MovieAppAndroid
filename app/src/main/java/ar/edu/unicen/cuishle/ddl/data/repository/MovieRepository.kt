package ar.edu.unicen.cuishle.ddl.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.util.query
import ar.edu.unicen.cuishle.ddl.data.remote.MovieApi
import ar.edu.unicen.cuishle.ddl.data.remote.MoviePagingSource
import ar.edu.unicen.cuishle.ddl.data.remote.MovieRemoteDataSource
import ar.edu.unicen.cuishle.ddl.data.mappers.toMovie
import ar.edu.unicen.cuishle.ddl.data.mappers.toMovieDetails
import ar.edu.unicen.cuishle.domain.models.Movie
import ar.edu.unicen.cuishle.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val remoteDataSource: MovieRemoteDataSource,
) {
    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }
    }

    fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    api = movieApi,
                    query = query
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }
    }

    suspend fun getSimilarMovies(id: Int): List<Movie> {
        val response = remoteDataSource.getSimilarMovies(id)
        return if (response.isSuccessful) {
            response.body()?.results?.map { it.toMovie() } ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getMovieDetails(id: Int): MovieDetails? =
        remoteDataSource.getMovieDetails(id)?.toMovieDetails()


}
