package com.netaphous.testapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netaphous.testapplication.data.JokeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val jokeProvider: JokeProvider
) : ViewModel() {
    private val _jokes = MutableStateFlow(ScreenState(showLoading = true))
    val jokes: StateFlow<ScreenState> = _jokes
    fun getJoke() {
        viewModelScope.launch {
            val jokes = jokeProvider.getJokesFromJson()
            _jokes.emit(ScreenState(jokes = jokes))
        }
    }
}