package com.netaphous.testapplication.ui

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import com.netaphous.testapplication.data.ColorPalette
import com.netaphous.testapplication.data.MainDispatcherExtension
import com.netaphous.testapplication.network.ColorPaletteProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class ScreenViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension(StandardTestDispatcher())

    /**
     * Data
     */
    private val fakePalette = ColorPalette(
        lightBackground = Color(49, 47, 49),
        lightAccent = Color(91, 83, 81),
        primary = Color(133, 155, 143),
        darkBackground = Color(226, 209, 167),
        darkAccent = Color(235, 198, 126),
    )

    /**
     * Dependencies
     */
    private val mockPaletteProvider: ColorPaletteProvider = mockk {
        coEvery { getColorPalette() } returns ColorPaletteProvider.Result.Success(fakePalette)
    }

    /**
     * System Under Test
     */
    private val sut = ScreenViewModel(mockPaletteProvider)


    @Test
    fun `GIVEN a new instance THEN screenState should default to loading`() {
        assertThat(sut.screenState.value).isEqualTo(ScreenState(showLoading = true)) // Initial
    }

    @Test
    fun `WHEN calling getNewPalette THEN post loading to the screen state`() = runTest {
        sut.getNewPalette()

        assertThat(sut.screenState.value).isEqualTo(ScreenState(showLoading = true))
    }

    @Test
    fun `GIVEN the provider returns success WHEN calling getNewPalette THEN update the palette`() = runTest {
        sut.getNewPalette()
        advanceUntilIdle()

        assertThat(sut.screenState.value).isEqualTo(ScreenState(palette = fakePalette))
    }

    @Test
    fun `GIVEN the provider returns failed WHEN calling getNewPalette THEN update the error message`() = runTest {
        val errorMessage = "Something went wrong!"
        coEvery { mockPaletteProvider.getColorPalette() } returns
                ColorPaletteProvider.Result.Failed(errorMessage)

        sut.getNewPalette()
        advanceUntilIdle()

        assertThat(sut.screenState.value).isEqualTo(ScreenState(errorMessage = errorMessage))
    }
}