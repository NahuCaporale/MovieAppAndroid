package ar.edu.unicen.cuishle.ddl.data.remote

import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieDetailsDTO
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieResponseDTO
import kotlinx.serialization.Required
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,

        ): Response<MovieResponseDTO>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: Int,

        ): Response<MovieDetailsDTO>

    @GET("search/movie")
    suspend fun getMovieBySearch(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieResponseDTO>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(@Path("movie_id") movieId: Int): Response<MovieResponseDTO>
}