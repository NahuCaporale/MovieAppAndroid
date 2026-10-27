package ar.edu.unicen.cuishle.domain.models

data class MovieDetails(
    val id: Int,
    val title: String,
    val voteAvg: Double,
    val imageUrl: String?,
    val overview: String?,
    val genres: List<String>,
    val releaseDate: String?,
    val homepage: String?,
    val backdropUrl: String?,
    val runtime : String,
) {
}