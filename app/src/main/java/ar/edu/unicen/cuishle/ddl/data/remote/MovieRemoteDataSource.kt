package ar.edu.unicen.cuishle.ddl.data.remote

import android.util.Log
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieDetailsDTO
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieResponseDTO
import ar.edu.unicen.cuishle.domain.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException


@Singleton
class MovieRemoteDataSource @Inject constructor(private val movieApi: MovieApi) {
    suspend fun getPopularMovies(page: Int = 1): MovieResponseDTO? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getPopularMovies(page)

                if (response.isSuccessful) response.body()
                else null


            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener las películas populares", e)
                null
            }
        }
    }

    suspend fun getMovieDetails(id: Int): MovieDetailsDTO? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovieDetails(id)//DTO
                if (response.isSuccessful)
                    response.body()
                else
                    null

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener los detalles de la pelicula", e)
                null
            }
        }

    }

    suspend fun getSimilarMovies(id: Int) =
        movieApi.getSimilar(id)

    private companion object {
        const val TAG = "MovieRemoteDataSource"
    }
}
