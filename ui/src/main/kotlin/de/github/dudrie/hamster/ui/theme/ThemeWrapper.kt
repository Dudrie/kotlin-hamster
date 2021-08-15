package de.github.dudrie.hamster.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import de.github.dudrie.hamster.ui.application.GameColorsLocal

/**
 * Wrapper composable which makes all theme related objects available through [CompositionLocals][androidx.compose.runtime.CompositionLocal].
 */
@Composable
fun ThemeWrapper(content: @Composable () -> Unit) {
    MaterialTheme {
        CompositionLocalProvider(GameColorsLocal provides GameColors) {
            content()
        }
    }
}
