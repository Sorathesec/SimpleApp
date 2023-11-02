package com.netaphous.testapplication.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

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
        val screenState by viewModel.jokes.collectAsState()

        when {
            screenState.showLoading -> LoadingScreen()
            screenState.jokes.isEmpty() -> EmptyScreen()
            else -> JokesList(screenState.jokes)
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

@Composable
private fun JokesList(jokes: List<Joke>) {
    LazyColumn {
        items(jokes) { joke ->
            JokeCard(joke)
        }
    }
}

@Composable
private fun JokeCard(joke: Joke) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = joke.iconUrl,
                contentDescription = null,
                modifier = Modifier.size(96.dp)
            )
            Spacer(Modifier.padding(8.dp))
            Column {
                Text(joke.value)
                val context = LocalContext.current
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(joke.url))
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go to Joke")
                }
            }
        }
    }
}