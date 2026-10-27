package ar.edu.unicen.cuishle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = primaryColor,
    onPrimary = white,
    secondary = secondaryColor,
    tertiary = tertiaryColor,
    background = white,
    onBackground = primaryColor,
    surface = white,
    onSurface = black,
    onSurfaceVariant = UnselectedGrayLight
)

private val DarkColors = darkColorScheme(
    primary = primaryColor,
    onPrimary = white,
    secondary = secondaryColor,
    tertiary = tertiaryColor,
    background = black,
    onBackground = secondaryColor,
    surface = black,
    onSurface = white,
    onSurfaceVariant = UnselectedGrayDark
)

@Composable
fun CuishleTheme(dark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) =
    MaterialTheme(
        colorScheme = if (dark) DarkColors else LightColors,
        typography = Typography,
        content = content
    )