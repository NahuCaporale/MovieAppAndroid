package ar.edu.unicen.cuishle.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import ar.edu.unicen.cuishle.ddl.data.mappers.toMovieUiModel
import ar.edu.unicen.cuishle.ddl.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject



@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab = _selectedTab.asStateFlow()

    fun onSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onIsSearchingChange(isSearching: Boolean) {
        _isSearching.value = isSearching
        if (!isSearching) {
            _searchQuery.value = ""
        }
    }

    fun onTabSelected(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }val searchMovies: Flow<PagingData<MovieUiModel>> =
        _searchQuery

            .debounce(300L)
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(PagingData.empty())
                } else {
                    movieRepository.searchMovies(query)
                }
            }
            .map { pagingData ->
                pagingData.map {
                    it.toMovieUiModel()
                }
            }
            .cachedIn(viewModelScope)

    val movies: Flow<PagingData<MovieUiModel>> = movieRepository.getPopularMovies()
        .map { pagingData ->
            pagingData.map { it.toMovieUiModel() }
        }
        .cachedIn(viewModelScope)
}
