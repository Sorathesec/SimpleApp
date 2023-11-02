package com.netaphous.testapplication.ui

data class ScreenState(
    val jokes: List<Joke> = emptyList(),
    val showLoading: Boolean = false,
)