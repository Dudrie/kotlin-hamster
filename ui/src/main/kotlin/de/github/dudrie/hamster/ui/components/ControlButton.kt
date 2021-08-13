package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.importer.helpers.ResourceReader

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
        val buttonIcon = remember(resourcePath) {
            ResourceReader(resourcePath).getContentAsImage().asImageBitmap()
        }
        Icon(
            bitmap = buttonIcon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(36.dp)
        )
    }
}
