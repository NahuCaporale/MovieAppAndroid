package ar.edu.unicen.cuishle.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.cuishle.ddl.data.mappers.toMovieUiModel
import ar.edu.unicen.cuishle.ddl.data.repository.WatchlistRepository
import ar.edu.unicen.cuishle.ui.movies.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(private val repository: WatchlistRepository) :
    ViewModel() {
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()
    val movies: StateFlow<List<MovieUiModel>> = repository.get()
        .map { listaDeMovies ->
            listaDeMovies.map { it.toMovieUiModel() }
        }
        .onEach { _loading.value = false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

}