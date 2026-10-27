package ar.edu.unicen.cuishle.ui.watchlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unicen.cuishle.R
import ar.edu.unicen.cuishle.ui.movies.MovieList

@Composable
fun WatchlistScreen(
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WatchlistViewModel = hiltViewModel(),
) {
    val isLoading by viewModel.loading.collectAsStateWithLifecycle()
    val movies by viewModel.movies.collectAsStateWithLifecycle()
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (movies.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = stringResource(R.string.no_movies_watchlist),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    } else {

        MovieList(
            modifier = modifier,
            movies = movies,
            onClick = onClick
        )
    }
}
