package ar.edu.unicen.cuishle.ddl.data.mappers

import ar.edu.unicen.cuishle.ddl.data.local.WatchlistEntity
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieDTO
import ar.edu.unicen.cuishle.ddl.data.remote.dto.MovieDetailsDTO
import ar.edu.unicen.cuishle.ddl.data.remote.dto.VideosResponseDTO
import ar.edu.unicen.cuishle.domain.models.Movie
import ar.edu.unicen.cuishle.domain.models.MovieDetails
import ar.edu.unicen.cuishle.ui.movies.MovieUiModel
import java.util.Locale

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342"

private fun String?.toImageUrl(): String? = this
    ?.takeIf { it.isNotBlank() }
    ?.let { "$IMAGE_BASE_URL$it" }

private fun VideosResponseDTO?.findTrailerKey(): String? {
    if (this == null) return null//filtamos la lista de videos quedevuelve imdb y nos quedamos con la que sea trailer y de youtube,
    // si no va a devolver null(no dibuja el boton de mirar trailer)

    val youtube = videos.filter { it.site == "YouTube" }

    val trailer = youtube.firstOrNull { it.type == "Trailer" }
    if (trailer != null) return trailer.key

    return youtube.firstOrNull()?.key
}

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
    runtime = runtime.toString(),
    video = videos.findTrailerKey()
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
    voteAvg = voteAvg.toStringRating(),
    imageUrl = imageUrl
)

fun Double.toStringRating(): String =
    String.format(Locale.getDefault(), "%.1f", this)
