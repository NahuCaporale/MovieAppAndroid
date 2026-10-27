package ar.edu.unicen.cuishle.ddl.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAvg: Double,
    @SerializedName("poster_path") val imageUrl: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("genres") val genres: List<GenreDTO>,
    @SerializedName("backdrop_path") val backdropUrl: String?,
    @SerializedName("runtime") val runtime: Int
)

data class GenreDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)