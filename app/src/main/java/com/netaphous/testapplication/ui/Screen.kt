package com.netaphous.testapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.netaphous.testapplication.R
import com.netaphous.testapplication.data.ColorPalette

@Composable
fun Screen() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        val viewModel: ScreenViewModel = viewModel()
        LaunchedEffect(viewModel) {
            viewModel.getNewPalette()
        }
        val screenState by viewModel.screenState.collectAsState()

        val errorMessage = screenState.errorMessage
        when {
            screenState.showLoading -> LoadingScreen()
            errorMessage != null -> ErrorScreen(errorMessage, viewModel::getNewPalette)
            else -> ColorPaletteScreen(screenState.palette, viewModel::getNewPalette)
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(errorMessage: String, fetchNewPalette: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_error_24),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Spacer(Modifier.padding(16.dp))
        Text(errorMessage)
        Spacer(Modifier.padding(16.dp))
        Button(onClick = fetchNewPalette) {
            Text("Try Again!")
        }
    }
}

@Composable
private fun ColorPaletteScreen(palette: ColorPalette, fetchNewPalette: () -> Unit) {
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
        verticalArrangement = Arrangement.Center,
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
        Spacer(Modifier.padding(8.dp))
        Text(
            text = stringResource(R.string.blurb),
            color = palette.primary
        )
        Spacer(Modifier.padding(16.dp))
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