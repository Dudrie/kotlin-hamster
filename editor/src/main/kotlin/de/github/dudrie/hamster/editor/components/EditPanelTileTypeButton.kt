package de.github.dudrie.hamster.editor.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.components.IconButtonWithText
import de.github.dudrie.hamster.ui.helpers.getResource

/**
 * Special button which shows the icon and the text corresponding to the [type].
 *
 * @param type [GameTileType] which this button should represent.
 * @param onClick Gets called with the [type] if the user clicks this button.
 * @param isSelected Is this button considered selected in regard to its [type]?
 * @param enabled Is this button enabled? Defaults to `true`.
 */
@Composable
fun EditPanelTileTypeButton(
    type: GameTileType,
    onClick: (type: GameTileType) -> Unit,
    isSelected: (type: GameTileType) -> Boolean,
    enabled: Boolean = true
) {
    IconButtonWithText(
        onClick = { onClick(type) },
        enabled = enabled,
        icon = {
            val icon = remember { ResourceReader(type.getResource()).getContentAsImage().asImageBitmap() }

            Image(
                icon,
                contentDescription = null,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(if (isSelected(type)) 0.5f else 0f) }),
                modifier = Modifier.size(40.dp)
            )
        },
        text = { Text(HamsterString.getForGameTileType(type)) }
    )
}
