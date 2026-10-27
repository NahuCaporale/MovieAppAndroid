package ar.edu.unicen.cuishle.ddl.data.remote.dto

import com.google.gson.annotations.SerializedName

class MovieResponseDTO(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDTO>,
    @SerializedName("total_pages") val totalPages: Int,
) {
}