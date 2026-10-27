package ar.edu.unicen.cuishle.ui.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ar.edu.unicen.cuishle.R

@Composable
fun MovieList(
    movies: LazyPagingItems<MovieUiModel>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(130.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        items(
            count = movies.itemCount//, key = movies.itemKey { it.id }, genera problemas en teoria si tmdb
        // devuelve mas de 1 vez la misma pelicula
        ) { index ->
            val movie = movies[index]
            if (movie != null) {
                MovieItem(
                    id = movie.id,
                    voteAvg = movie.voteAvg,
                    title = movie.title,
                    imageUrl = movie.imageUrl ?: "",
                    onClick = onClick
                )
            }
        }
        if (movies.loadState.append is LoadState.Loading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (movies.loadState.append is LoadState.Error) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(onClick = { movies.retry() }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
        }
    }
}

@Composable
//funcion para watchlist
fun MovieList(
    movies: List<MovieUiModel>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 130.dp),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieItem(
                id = movie.id,
                voteAvg = movie.voteAvg,
                title = movie.title,
                imageUrl = movie.imageUrl ?: "",
                onClick = onClick
            )
        }
    }
}


