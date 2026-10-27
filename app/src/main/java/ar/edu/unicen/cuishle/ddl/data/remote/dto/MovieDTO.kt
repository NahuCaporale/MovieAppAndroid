package ar.edu.unicen.cuishle.ddl.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAvg: Double,
    @SerializedName("poster_path") val imageUrl: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("backdrop_path") val backdropUrl: String?
)