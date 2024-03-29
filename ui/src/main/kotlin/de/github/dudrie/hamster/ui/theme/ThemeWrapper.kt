package de.github.dudrie.hamster.ui.theme

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Wrapper composable which makes all theme related objects available through [CompositionLocals][androidx.compose.runtime.CompositionLocal].
 */
@Composable
fun ThemeWrapper(content: @Composable () -> Unit) {
    val shapes = remember { Shapes(medium = RoundedCornerShape(8.dp)) }
    val colors = remember {
        lightColors(
            primary = Color(0xFF0d47a1),
            primaryVariant = Color(0xFF1565C0),
            onPrimary = Color.White,
            secondary = Color(0xFFd84315),
            secondaryVariant = Color(0xFFe64a19),
            onSecondary = Color.Black
        )
    }

    MaterialTheme(colors = colors, shapes = shapes) {
        val scrollbarStyle = LocalScrollbarStyle.current.copy(
            unhoverColor = Color.Black.copy(alpha = 0.2f),
            shape = RoundedCornerShape(50),
        )

        CompositionLocalProvider(
            GameColorsLocal provides GameColors,
            GameTypographyLocal provides GameTypography,
            LocalScrollbarStyle provides scrollbarStyle
        ) {
            content()
        }
    }
}
