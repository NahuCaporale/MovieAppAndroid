package ar.edu.unicen.cuishle.ui.profile

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
class ProfileViewModel @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val movies: StateFlow<List<MovieUiModel>> =
        watchlistRepository.get().map { lista -> lista.map { it.toMovieUiModel() } }
            .onEach { lista -> _count.value = lista.size }
            .onEach { _loading.value = false }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            );
}