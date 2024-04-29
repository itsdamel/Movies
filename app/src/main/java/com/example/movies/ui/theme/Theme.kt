package com.example.movies.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val ColorScheme = lightColorScheme(
    background = LightBlack,
    onBackground = White,
    tertiary = Grey,
    primaryContainer = Blue,
    secondary = Yellow,
    onPrimaryContainer = Black,

    )

@Composable
fun MoviesTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}