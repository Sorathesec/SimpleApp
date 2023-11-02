package com.netaphous.testapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.netaphous.testapplication.data.Screen
import com.netaphous.testapplication.ui.components.RenderComponent

@Composable
fun Screen() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        val viewModel: ScreenViewModel = viewModel()
        LaunchedEffect(viewModel) {
            viewModel.getJoke()
        }
        val screenState by viewModel.primer.collectAsState()

        when {
            screenState.showLoading -> LoadingScreen()
            else -> screenState.currentScreen?.let { screen ->
                ScreenContent(
                    currentScreen = screen,
                    onEvent = viewModel::onEvent,
                    onInput = viewModel::onInput,
                )
            } ?: EmptyScreen()
        }
    }
}

@Composable
fun ScreenContent(
    currentScreen: Screen,
    onEvent: (String) -> Unit,
    onInput: (id: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        currentScreen.components.forEach { component ->
            RenderComponent(
                component = component,
                onEvent = onEvent,
                onInput = onInput
            )
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
private fun EmptyScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "No jokes today!",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
