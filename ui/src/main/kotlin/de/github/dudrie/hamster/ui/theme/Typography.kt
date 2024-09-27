package de.github.dudrie.hamster.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object SpielTypography {

    /**
     * [TextStyle] für die Anzahl an Körnern auf dem Spielfeld
     */
    val kornAnzahl: TextStyle
        @Composable
        get() = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
}

/**
 * Ermöglicht den Zugriff auf das [SpielTypography] object.
 */
internal val SpielTypographyLocal = compositionLocalOf { SpielTypography }
