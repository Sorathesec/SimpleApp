package com.netaphous.testapplication.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netaphous.testapplication.data.Button
import com.netaphous.testapplication.data.Text

@Composable
fun RenderButton(
    button: Button,
    onEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Button(
        onClick = { onEvent(button.onClick) },
        modifier = modifier,
    ) {
        Text(button.text)
    }
}