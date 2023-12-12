package com.netaphous.testapplication.ui

import com.netaphous.testapplication.data.MainDispatcherExtension
import com.netaphous.testapplication.network.ColorPaletteProvider
import io.mockk.mockk
import org.junit.jupiter.api.extension.RegisterExtension

class ScreenViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    /**
     * Dependencies
     */
    private val mockPaletteProvider: ColorPaletteProvider = mockk()

    /**
     * System Under Test
     */
    private val sut = ScreenViewModel(mockPaletteProvider)

    // TODO
}