package ar.edu.unicen.cuishle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = primaryLightColor,
    onPrimary = white,
    secondary = secondaryColor,
    tertiary = TealAccentLight,
    onTertiary = white,
    background = white,
    onBackground = black,
    surface = white,
    onSurface = black,
    onSurfaceVariant = UnselectedGrayLight
)

private val DarkColors = darkColorScheme(
    primary = primaryDarkColor,
    onPrimary = black,
    secondary = secondaryColor,
    tertiary = TealAccentLight,
    onTertiary = white,
    background = black,
    onBackground = tertiaryColor,
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