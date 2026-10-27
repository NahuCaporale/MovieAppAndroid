package ar.edu.unicen.cuishle.di

import android.content.Context
import androidx.room.Room
import ar.edu.unicen.cuishle.ddl.data.local.WatchlistDatabase
import ar.edu.unicen.cuishle.ddl.data.local.dao.WatchlistDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WatchlistModule {
    @Provides
    @Singleton
    fun provideWatchlistDatabase(
        @ApplicationContext context: Context
    ): WatchlistDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = WatchlistDatabase::class.java,
            name = "watchlist_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWatchlistDao(database: WatchlistDatabase): WatchlistDAO {
        return database.watchlistDAO()
    }
}