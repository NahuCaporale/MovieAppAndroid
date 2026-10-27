package ar.edu.unicen.cuishle.ddl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unicen.cuishle.ddl.data.local.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDAO {
    @Query("SELECT * FROM watchlist_movies")
    fun getWatchlist(): Flow<List<WatchlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWatchlist(movie: WatchlistEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM watchlist_movies WHERE id = :id)")
    fun isInWatchlist(id: Int): Flow<Boolean>

    @Query("DELETE FROM watchlist_movies WHERE id = :id")
    suspend fun remove(id: Int)
}