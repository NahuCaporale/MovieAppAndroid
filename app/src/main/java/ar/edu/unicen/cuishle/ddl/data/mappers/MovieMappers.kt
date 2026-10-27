package ar.edu.unicen.cuishle.ddl.data.mappers

import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieDTO
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieDetailsDTO
import ar.edu.unicen.cuishle.ddl.data.local.WatchlistEntity
import ar.edu.unicen.cuishle.domain.models.Movie
import ar.edu.unicen.cuishle.domain.models.MovieDetails
import ar.edu.unicen.cuishle.ui.movies.MovieUiModel
import java.util.Locale

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342"
private fun String?.toImageUrl(): String? = this
    ?.takeIf { it.isNotBlank() }
    ?.let { "$IMAGE_BASE_URL$it" }

fun MovieDTO.toMovie() = Movie(
    id = id,
    title = title,
    voteAvg = voteAvg,
    imageUrl = imageUrl.toImageUrl(),
    backdrop = backdropUrl.toImageUrl()
)

fun MovieDetailsDTO.toMovieDetails() = MovieDetails(
    id = id,
    title = title,
    voteAvg = voteAvg,
    imageUrl = imageUrl.toImageUrl(),
    overview = overview,
    genres = genres.map { it.name },
    releaseDate = releaseDate,
    backdropUrl = backdropUrl.toImageUrl(),
    homepage = homepage?.takeIf { it.isNotBlank() },
    runtime = runtime.toString()
)

fun WatchlistEntity.toMovie() = Movie(
    id = id,
    title = title,
    voteAvg = voteAvg,
    imageUrl = image,
    backdrop = backdrop
)

fun Movie.toMovieUiModel() = MovieUiModel(
    id = id,
    title = title,
    voteAvg =  voteAvg.toStringRating(),
    imageUrl = imageUrl,

    )
fun Double.toStringRating(): String =
    String.format(Locale.getDefault(), "%.1f", this)