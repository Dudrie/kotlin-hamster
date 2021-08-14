package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.importer.helpers.ResourceReader

enum class ResourceIconSize(val value: Dp) {
    Small(24.dp), Medium(36.dp), Large(48.dp)
}

@Composable
fun ResourceIcon(
    resourcePath: String,
    modifier: Modifier = Modifier,
    size: ResourceIconSize = ResourceIconSize.Small,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    val icon = remember(resourcePath) {
        ResourceReader(resourcePath).getContentAsImage().asImageBitmap()
    }
    Icon(
        bitmap = icon,
        contentDescription = null,
        tint = tint,
        modifier = modifier.size(size.value)
    )
}
