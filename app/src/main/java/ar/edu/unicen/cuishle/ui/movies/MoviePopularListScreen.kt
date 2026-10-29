package ar.edu.unicen.cuishle.ui.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ar.edu.unicen.cuishle.R
import ar.edu.unicen.cuishle.ui.components.MessageError
import ar.edu.unicen.cuishle.ui.components.CuishleHeader
import ar.edu.unicen.cuishle.ui.components.CuishleTabs
import ar.edu.unicen.cuishle.ui.theme.CuishleTheme
import ar.edu.unicen.cuishle.ui.watchlist.WatchlistScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviePopularListScreen(
    onClick: (Int) -> Unit,
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = hiltViewModel(),

) {
    val moviePaging = viewModel.movies.collectAsLazyPagingItems()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val moviesSearched = viewModel.searchMovies.collectAsLazyPagingItems()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (isSearching) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { newText ->
                    viewModel.onSearchQuery(newText)
                },
                onSearch = {
                    keyboardController?.hide()//activa la busqueda y esconde el teclado
                },
                active = true,
                onActiveChange = { active ->
                    viewModel.onIsSearchingChange(active)
                },
                placeholder = {
                    Text(stringResource(R.string.search))
                },
                leadingIcon = {
                    IconButton(onClick = {
                        viewModel.onIsSearchingChange(false)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                when {
                    moviesSearched.loadState.refresh is LoadState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    moviesSearched.loadState.refresh is LoadState.Error -> {
                        MessageError(
                            message = stringResource(R.string.error_search),
                            onRetry = { moviesSearched.retry() }
                        )
                    }

                    moviesSearched.itemCount == 0 && searchQuery.isNotBlank() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_movies_found),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    else -> {
                        MovieList(movies = moviesSearched, onClick = onClick)
                    }
                }
            }
        } else {
            CuishleHeader(onProfileClick = onProfileClick)
            CuishleTabs(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    viewModel.onTabSelected(tab)
                },
                onSearchClick = {
                    viewModel.onIsSearchingChange(true)
                }
            )
        }

        if (!isSearching) {
            when (selectedTab) {
                0 -> {
                    when {
                        moviePaging.loadState.refresh is LoadState.Loading && moviePaging.itemCount == 0 -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        moviePaging.loadState.refresh is LoadState.Error && moviePaging.itemCount == 0 -> {
                            MessageError(
                                message = stringResource(R.string.error_popular),
                                onRetry = { moviePaging.retry() }
                            )
                        }

                        else -> {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(R.string.popular),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, top = 20.dp, bottom = 10.dp),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                MovieList(
                                    modifier = Modifier.weight(1f),
                                    movies = moviePaging,
                                    onClick = onClick
                                )
                            }
                        }
                    }
                }

                1 -> {
                    Box(modifier = Modifier.weight(1f)) {
                        WatchlistScreen(onClick = onClick)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviePopularListScreenPreview() {
    CuishleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}
