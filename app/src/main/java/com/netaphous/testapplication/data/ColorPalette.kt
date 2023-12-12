package com.netaphous.testapplication.data

import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val lightBackground: Color,
    val lightAccent: Color,
    val primary: Color,
    val darkBackground: Color,
    val darkAccent: Color,
) {
    companion object {
        val Default = ColorPalette(
                lightBackground = Color.White,
                lightAccent = Color.DarkGray,
                primary = Color.Blue,
                darkBackground = Color.LightGray,
                darkAccent = Color.Black
            )
    }
}