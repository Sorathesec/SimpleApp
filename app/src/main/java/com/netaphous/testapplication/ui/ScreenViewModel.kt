package com.netaphous.testapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netaphous.testapplication.network.ColorPaletteProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel @Inject constructor(
    private val colorPaletteProvider: ColorPaletteProvider,
) : ViewModel() {
    private val _screenState = MutableStateFlow(ScreenState(showLoading = true))
    val screenState: StateFlow<ScreenState> = _screenState
    fun getNewPalette() {
        _screenState.value = ScreenState(showLoading = true)
        viewModelScope.launch {
            _screenState.value = when (val result = colorPaletteProvider.getColorPalette()) {
                is ColorPaletteProvider.Result.Success ->
                    ScreenState(palette = result.palette)
                is ColorPaletteProvider.Result.Failed ->
                    ScreenState(errorMessage = result.errorMessage)
            }
        }
    }
}