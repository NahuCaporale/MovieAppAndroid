package ar.edu.unicen.cuishle.ddl.data.repository

import ar.edu.unicen.cuishle.ddl.data.local.dao.WatchlistDAO
import ar.edu.unicen.cuishle.ddl.data.local.WatchlistEntity
import ar.edu.unicen.cuishle.ddl.data.mappers.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistRepository @Inject constructor(private val watchlistDAO: WatchlistDAO) {
    fun get() =
        watchlistDAO.getWatchlist().map { movies -> movies.map { movie -> movie.toMovie() } }

    suspend fun add(movie: WatchlistEntity) {
        watchlistDAO.insertMovieWatchlist(movie)
    }

    fun isInWatchlist(id: Int): Flow<Boolean> = watchlistDAO.isInWatchlist(id)

    suspend fun remove(id: Int) {
        watchlistDAO.remove(id)
    }
}