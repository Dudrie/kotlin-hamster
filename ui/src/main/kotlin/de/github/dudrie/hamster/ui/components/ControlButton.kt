package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
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

object ControlButtonColors : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> = mutableStateOf(Color.Transparent)

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return if (enabled) {
            mutableStateOf(MaterialTheme.colors.onPrimary)
        } else {
            mutableStateOf(Color.LightGray)
        }
    }
}

@Composable
fun ControlButton(resourcePath: String, onClick: () -> Unit, enabled: Boolean = true, modifier: Modifier = Modifier) {
    val contentColor by ControlButtonColors.contentColor(enabled)

    OutlinedButton(
        onClick = onClick,
        colors = ControlButtonColors,
        border = BorderStroke(1.dp, contentColor),
        modifier = modifier.defaultMinSize(minWidth = 48.dp),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled
    ) {
        ResourceIcon(resourcePath, size = ResourceIconSize.Medium, tint = contentColor)
    }
}
