package com.example.virtualmailbox.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = androidx.compose.ui.graphics.Color(0xFF1976D2),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF004BA0),
    secondary = androidx.compose.ui.graphics.Color(0xFFD81B60)
)

private val LightColorPalette = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF2196F3),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF1976D2),
    secondary = androidx.compose.ui.graphics.Color(0xFFF50057)
)

@Composable
fun VirtualMailboxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = androidx.compose.material.Typography(),
        shapes = androidx.compose.material.Shapes(),
        content = content
    )
}
