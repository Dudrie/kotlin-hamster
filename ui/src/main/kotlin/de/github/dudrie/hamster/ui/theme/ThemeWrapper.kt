package de.github.dudrie.hamster.ui.theme

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import de.github.dudrie.hamster.ui.application.GameColorsLocal

/**
 * Wrapper composable which makes all theme related objects available through [CompositionLocals][androidx.compose.runtime.CompositionLocal].
 */
@Composable
fun ThemeWrapper(content: @Composable () -> Unit) {
    DesktopMaterialTheme {
        val scrollbarStyle = LocalScrollbarStyle.current.copy(
            unhoverColor = Color.Black.copy(alpha = 0.2f),
            shape = RoundedCornerShape(50),
        )

        CompositionLocalProvider(GameColorsLocal provides GameColors, LocalScrollbarStyle provides scrollbarStyle) {
            content()
        }
    }
}
