package ar.edu.unicen.cuishle.ddl.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist_movies")
data class WatchlistEntity(

    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String?,
    val backdrop: String?,
    val voteAvg: Double
)