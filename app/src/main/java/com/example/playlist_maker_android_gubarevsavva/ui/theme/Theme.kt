package com.example.playlist_maker_android_gubarevsavva.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun PlaylistmakerandroidGubarevSavvaTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = darkColorScheme(
        primary = White,
        onPrimary = Color.Black,
        secondary = White,
        onSecondary = Color.Black,
        background = Black,
        onBackground = White,
        surface = Black,
        onSurface = White,
        outline = Gray
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
