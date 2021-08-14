package de.github.dudrie.hamster.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ThemeWrapper(content: @Composable () -> Unit) {
    MaterialTheme {
        CompositionLocalProvider(GameColorsLocal provides GameColors) {
            content()
        }
    }
}
