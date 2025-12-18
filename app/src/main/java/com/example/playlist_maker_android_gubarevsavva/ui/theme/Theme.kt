package com.example.playlist_maker_android_gubarevsavva.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val PixelShapes = Shapes(
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(0.dp),
    large = RoundedCornerShape(0.dp)
)

@Composable
fun PlaylistmakerandroidGubarevSavvaTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
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
    } else {
        lightColorScheme(
            primary = Color.Black,
            onPrimary = Color.White,
            secondary = Color.Black,
            onSecondary = Color.White,
            background = White,
            onBackground = Color.Black,
            surface = White,
            onSurface = Color.Black,
            outline = Gray
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = PixelShapes,
        typography = Typography,
        content = content
    )
}
