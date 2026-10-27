package ar.edu.unicen.cuishle.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.cuishle.ddl.data.repository.MovieRepository
import ar.edu.unicen.cuishle.ddl.data.local.WatchlistEntity
import ar.edu.unicen.cuishle.ddl.data.mappers.toMovieUiModel
import ar.edu.unicen.cuishle.ddl.data.repository.WatchlistRepository
import ar.edu.unicen.cuishle.domain.models.MovieDetails
import ar.edu.unicen.cuishle.ui.movies.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val watchlistRepository: WatchlistRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val movieId: Int? = savedStateHandle.get<String>("id")?.toIntOrNull()
        ?: savedStateHandle.get<Int>("id")
    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _similarMovies = MutableStateFlow<List<MovieUiModel>>(emptyList())
    val similarMovie = _similarMovies.asStateFlow()

    val isFavorite: StateFlow<Boolean> = if (movieId != null) {
        watchlistRepository.isInWatchlist(movieId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )
    } else {
        MutableStateFlow(false)
    }


    fun toggleWatchlist(movie: MovieDetails) {
        viewModelScope.launch {
            if (isFavorite.value) {
                watchlistRepository.remove(movie.id)
            } else {
                watchlistRepository.add(
                    WatchlistEntity(
                        id = movie.id,
                        title = movie.title,
                        image = movie.imageUrl,
                        backdrop = movie.backdropUrl,
                        voteAvg = movie.voteAvg
                    )
                )
            }
        }
    }

    private val _details = MutableStateFlow<MovieDetails?>(null)
    val details = _details.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        retry()
    }

    fun getDetails(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = false
            try {
                val result = repository.getMovieDetails(id)
                if (result != null) {
                    _similarMovies.value = repository.getSimilarMovies(id).map {
                        it.toMovieUiModel()
                    }
                    _details.value = result

                } else {
                    _error.value = true

                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun retry() {
        movieId?.let { getDetails(it) } ?: run { _error.value = true }
    }
}
