package ar.edu.unicen.cuishle.ddl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unicen.cuishle.ddl.data.local.dao.WatchlistDAO

@Database(
    entities = [WatchlistEntity::class],
    version = 2
)
abstract class WatchlistDatabase : RoomDatabase() {
    abstract fun watchlistDAO(): WatchlistDAO

}