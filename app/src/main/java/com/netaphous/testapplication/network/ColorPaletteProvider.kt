package com.netaphous.testapplication.network

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.netaphous.testapplication.data.ColorPalette
import com.netaphous.testapplication.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ColorPaletteProvider @Inject constructor(
    private val api: ColormindApi,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun getColorPalette(): Result = withContext(dispatcher) {
        runCatching {
            api.getColorPalette()
        }.mapCatching { response ->
            if (response.isSuccessful) {
                val body = requireNotNull(response.body()) {
                    "Response had no body"
                }
                val colorsFromArray = body.result.map { array ->
                    Color(
                        array.getOrElse(0) { 0 },
                        array.getOrElse(1) { 0 },
                        array.getOrElse(2) { 0 },
                    )
                }
                val palette = ColorPalette(
                    lightBackground = colorsFromArray.getOrElse(0) { Color.White },
                    lightAccent = colorsFromArray.getOrElse(1) { Color.White },
                    primary = colorsFromArray.getOrElse(2) { Color.White },
                    darkBackground = colorsFromArray.getOrElse(3) { Color.White },
                    darkAccent = colorsFromArray.getOrElse(4) { Color.White },
                )
                return@withContext Result.Success(palette)
            } else {
                return@withContext Result.Failed("Network Error")
            }
        }.getOrElse {
            Log.d(this::class.java.name, it.toString())
            return@withContext Result.Failed("Something went wrong!")
        }
    }

    sealed interface Result {
        data class Success(val palette: ColorPalette) : Result
        data class Failed(val errorMessage: String) : Result
    }
}