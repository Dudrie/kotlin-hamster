package de.github.dudrie.hamster.ui.theme

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun ThemeWrapper(inhalt: @Composable () -> Unit) {
    val formen = remember { Shapes(medium = RoundedCornerShape(8.dp)) }
    val farben = remember { lightScheme }

    MaterialTheme(colorScheme = farben, shapes = formen) {
        val scrollbarStyle =
            LocalScrollbarStyle.current.copy(
                unhoverColor = farben.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(50)
            )

        CompositionLocalProvider(LocalScrollbarStyle provides scrollbarStyle) {
            inhalt()
        }
    }
}


