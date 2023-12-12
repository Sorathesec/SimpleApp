package com.netaphous.testapplication.ui

import com.netaphous.testapplication.data.ColorPalette

data class ScreenState(
    val palette: ColorPalette = ColorPalette.Default,
    val showLoading: Boolean = false,
    val errorMessage: String? = null,
)