package com.netaphous.testapplication.ui
import com.netaphous.testapplication.data.Screen

data class ScreenStateP(
    val currentScreen:Screen? = null,
    val showLoading: Boolean = false,
)