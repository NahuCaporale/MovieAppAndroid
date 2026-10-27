package ar.edu.unicen.cuishle.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.unicen.cuishle.ui.details.MovieDetailsScreen
import ar.edu.unicen.cuishle.ui.movies.MoviePopularListScreen
import ar.edu.unicen.cuishle.ui.profile.ProfileScreen
import ar.edu.unicen.cuishle.ui.theme.CuishleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CuishleTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "movies"
                    ) {
                        composable(route = "movies") {
                            MoviePopularListScreen(
                                onClick = { id -> navController.navigate("movie/$id") },
                                onProfileClick = { navController.navigate("profile") }
                            )
                        }
                        composable(route = "movie/{id}") {
                            MovieDetailsScreen(
                                onMovieClick = { newMovieId ->
                                    navController.navigate("movie/$newMovieId")
                                },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(route = "profile") {
                            ProfileScreen(
                                onBackClick = { navController.popBackStack() },
                                onMovieClick = { movieId -> navController.navigate("movie/${movieId}") })
                        }
                    }
                }
            }
        }
    }
}
