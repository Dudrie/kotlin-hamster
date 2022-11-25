package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.importer.helpers.ResourceReader

/**
 * Sizes for the [Icon] used in the [ResourceIcon] composable.
 *
 * @param value The [Dp] of the related [ResourceIconSize].
 */
enum class ResourceIconSize(val value: Dp) {
    /**
     * Small icon
     */
    Small(24.dp),

    /**
     * Medium icon
     */
    Medium(36.dp),

    /**
     * Large icon
     */
    Large(48.dp)
}

/**
 * Shows an [Icon] which gets loaded from the resources.
 *
 * The icon content is defined by the given [resourcePath].
 */
@Composable
fun ResourceIcon(
    resourcePath: String,
    modifier: Modifier = Modifier,
    size: ResourceIconSize = ResourceIconSize.Small,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    val icon = remember(resourcePath) {
        ResourceReader(resourcePath).getContentAsImage().toComposeImageBitmap()
    }
    Icon(
        bitmap = icon,
        contentDescription = null,
        tint = tint,
        modifier = modifier.size(size.value)
    )
}
