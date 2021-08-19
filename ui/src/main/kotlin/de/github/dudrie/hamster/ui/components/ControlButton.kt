package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.components.appbar.AppBarButton

/**
 * Implementation of [ButtonColors] to be used in the [ControlButton].
 */
object ControlButtonColors : ButtonColors {
    /**
     * Represents the background color for this button, depending on [enabled].
     *
     * @param enabled whether the button is enabled
     */
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> = mutableStateOf(Color.Transparent)

    /**
     * Represents the content color for this button, depending on [enabled].
     *
     * @param enabled whether the button is enabled
     */
    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return if (enabled) {
            mutableStateOf(MaterialTheme.colors.onPrimary)
        } else {
            mutableStateOf(Color.LightGray)
        }
    }
}

/**
 * [OutlinedButton] adjusted to be placed upon the [AppBar][de.github.dudrie.hamster.ui.components.appbar.AppBar].
 *
 * It shows the icon defined by [resourcePath] as only content.
 *
 * @see ResourceIcon
 */
@Composable
fun ControlButton(resourcePath: String, onClick: () -> Unit, enabled: Boolean = true, modifier: Modifier = Modifier) {
    val contentColor by ControlButtonColors.contentColor(enabled)

    AppBarButton(onClick = onClick, enabled = enabled, contentPadding = PaddingValues(0.dp), modifier = modifier) {
        ResourceIcon(resourcePath, size = ResourceIconSize.Medium, tint = contentColor)
    }
}
