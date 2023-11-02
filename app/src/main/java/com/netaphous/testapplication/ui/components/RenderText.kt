package com.netaphous.testapplication.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netaphous.testapplication.data.Text

@Composable
fun RenderText(text: Text, modifier: Modifier = Modifier) {
    Text(
        text = text.text,
        modifier = modifier
    )
}