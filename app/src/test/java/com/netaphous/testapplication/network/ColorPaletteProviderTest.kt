package com.netaphous.testapplication.network

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth
import com.netaphous.testapplication.data.ColorPalette
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ColorPaletteProviderTest {

    /**
     * Data
     */
    private val validResponse = ColormindDto.Response(
        result = listOf(
            listOf(49, 47, 49),
            listOf(91, 83, 81),
            listOf(133, 155, 143),
            listOf(226, 209, 167),
            listOf(235, 198, 126)
        )
    )
    private val equivalentPalette = ColorPalette(
        lightBackground = Color(49, 47, 49),
        lightAccent = Color(91, 83, 81),
        primary = Color(133, 155, 143),
        darkBackground = Color(226, 209, 167),
        darkAccent = Color(235, 198, 126),
    )

    /**
     * Mocks
     */
    private val mockResponse: Response<ColormindDto.Response> = mockk {
        every { isSuccessful } returns true
        every { body() } returns validResponse
    }

    /**
     * Dependenices
     */
    private val mockApi: ColormindApi = mockk {
        coEvery { getColorPalette() } returns mockResponse
    }
    private val testDispatcher = UnconfinedTestDispatcher()

    /**
     * System under test
     */
    private val sut = ColorPaletteProvider(mockApi, testDispatcher)

    @Test
    fun `GIVEN a successful network response WHEN calling getColorPalette THEN return success`() =
        runTest {
            val result = sut.getColorPalette()

            Truth.assertThat(result).isInstanceOf(ColorPaletteProvider.Result.Success::class.java)
            Truth.assertThat((result as ColorPaletteProvider.Result.Success).palette)
                .isEqualTo(equivalentPalette)
        }

    @Test
    fun `GIVEN a failed network response WHEN calling getColorPalette THEN return failed with a network error response`() =
        runTest {
            every { mockResponse.isSuccessful } returns false

            val result = sut.getColorPalette()

            Truth.assertThat(result).isInstanceOf(ColorPaletteProvider.Result.Failed::class.java)
            Truth.assertThat((result as ColorPaletteProvider.Result.Failed).errorMessage)
                .isEqualTo("Network Error")
        }

    @Test
    fun `GIVEN an exception is thrown by the network call WHEN calling getColorPalette THEN return failed with a network error response`() =
        runTest {
            coEvery { mockApi.getColorPalette() } throws Exception("We fell over!")

            val result = sut.getColorPalette()

            Truth.assertThat(result).isInstanceOf(ColorPaletteProvider.Result.Failed::class.java)
            Truth.assertThat((result as ColorPaletteProvider.Result.Failed).errorMessage)
                .isEqualTo("Something went wrong!")
        }

    @Test
    fun `GIVEN an invalid color value in the response WHEN calling getColorPalette THEN return success with defaulted values`() =
        runTest {
            every { mockResponse.body() } returns ColormindDto.Response(
                listOf(
                    listOf(49, 47),
                    listOf(91, 83, 81),
                    listOf(133, 155, 143),
                    listOf(226, 209, 167),
                    listOf(235, 198, 126)
                )
            )

            val result = sut.getColorPalette()

            Truth.assertThat(result).isInstanceOf(ColorPaletteProvider.Result.Success::class.java)
            Truth.assertThat((result as ColorPaletteProvider.Result.Success).palette.lightBackground)
                .isEqualTo(Color(49, 47, 0))
        }

    @Test
    fun `GIVEN a missing color value in the response WHEN calling getColorPalette THEN return success with defaulted values`() =
        runTest {
            every { mockResponse.body() } returns ColormindDto.Response(
                listOf(
                    listOf(91, 83, 81),
                    listOf(133, 155, 143),
                    listOf(226, 209, 167),
                    listOf(235, 198, 126)
                )
            )

            val result = sut.getColorPalette()

            Truth.assertThat(result).isInstanceOf(ColorPaletteProvider.Result.Success::class.java)
            Truth.assertThat((result as ColorPaletteProvider.Result.Success).palette.darkAccent)
                .isEqualTo(Color.White)
        }
}