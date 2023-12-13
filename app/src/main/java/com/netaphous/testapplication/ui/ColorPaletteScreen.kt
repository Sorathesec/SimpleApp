package com.netaphous.testapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.netaphous.testapplication.R
import com.netaphous.testapplication.data.ColorPalette
import com.netaphous.testapplication.ui.theme.TestApplicationTheme

@Composable
internal fun ColorPaletteScreen(palette: ColorPalette, fetchNewPalette: () -> Unit) {
    val accent = if (isSystemInDarkTheme()) {
        palette.darkAccent
    } else {
        palette.lightAccent
    }
    val background = if (isSystemInDarkTheme()) {
        palette.darkBackground
    } else {
        palette.lightBackground
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier
                .background(accent)
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Text(
            text = stringResource(R.string.blurb),
            color = palette.primary
        )
        Button(
            onClick = fetchNewPalette,
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.primary,
            )
        ) {
            Text("Try Another!")
        }
    }
}

@Preview
@Composable
fun ColorPaletteScreenPreview() {
    TestApplicationTheme {
        ColorPaletteScreen(
            palette = ColorPalette(
                lightBackground = Color(49, 47, 49),
                lightAccent = Color(91, 83, 81),
                primary = Color(133, 155, 143),
                darkBackground = Color(226, 209, 167),
                darkAccent = Color(235, 198, 126),
            ),
            fetchNewPalette = {})
    }
}