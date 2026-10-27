package ar.edu.unicen.cuishle.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val voteAvg: Double,
    val imageUrl: String?,
    val backdrop : String?
) {}