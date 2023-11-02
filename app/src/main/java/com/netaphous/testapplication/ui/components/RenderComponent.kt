package com.netaphous.testapplication.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netaphous.testapplication.data.Button
import com.netaphous.testapplication.data.Component
import com.netaphous.testapplication.data.Container
import com.netaphous.testapplication.data.Input
import com.netaphous.testapplication.data.Text

@Composable
fun RenderComponent(
    component: Component,
    onEvent: (String) -> Unit,
    onInput: (id: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (component) {
        is Container -> RenderContainer(
            container = component,
            onEvent = onEvent,
            onInput = onInput,
            modifier = modifier
        )
        is Button -> RenderButton(
            button = component,
            onEvent = onEvent,
            modifier = modifier
        )
        is Input -> RenderInput(
            input = component,
            onInput = onInput,
            modifier = modifier
        )
        is Text -> RenderText(
            text = component,
            modifier = modifier
        )
    }
}