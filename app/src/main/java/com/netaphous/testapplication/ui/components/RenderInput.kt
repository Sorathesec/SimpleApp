package com.netaphous.testapplication.ui.components

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.netaphous.testapplication.data.Input
import com.netaphous.testapplication.data.Text

@Composable
fun RenderInput(
    input: Input,
    onInput: (id: String, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }
    TextField(
        value = value,
        onValueChange = {
            value = it
            onInput(input.id, it)
        },
        placeholder = {
            Text(input.hint)
        },
        modifier = modifier
    )
}