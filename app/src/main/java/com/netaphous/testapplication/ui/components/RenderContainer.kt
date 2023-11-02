package com.netaphous.testapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netaphous.testapplication.data.Container

@Composable
fun RenderContainer(
    container: Container,
    onEvent: (String) -> Unit,
    onInput: (id: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        container.components.forEach { component ->
            RenderComponent(
                component = component,
                onEvent = onEvent,
                onInput = onInput,
                modifier = modifier
            )
        }
    }
}