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
    private val _palette = MutableStateFlow(ScreenState(showLoading = true))
    val palette: StateFlow<ScreenState> = _palette
    fun getNewPalette() {
        viewModelScope.launch {
            _palette.emit(ScreenState(showLoading = true))
            _palette.emit(
                when (val result = colorPaletteProvider.getColorPalette()) {
                    is ColorPaletteProvider.Result.Success ->
                        ScreenState(palette = result.palette)
                    is ColorPaletteProvider.Result.Failed ->
                        ScreenState(errorMessage = result.errorMessage)
                }
            )
        }
    }
}