package de.github.dudrie.hamster.editor.sidepanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.editor.model.EditableGameTile
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.components.IconButtonWithText
import de.github.dudrie.hamster.ui.components.TextFieldForNumbers
import de.github.dudrie.hamster.ui.helpers.getResource

/**
 * Edit panel that allows to change the configuration of the given [tile].
 */
@Composable
fun EditPanelTile(tile: EditableGameTile) {
    Row(Modifier.padding(bottom = 16.dp)) {
        // TODO: Abstract me because I only depend on the GameTileType - rest is the same code.
        //       Keep it DRY!
        IconButtonWithText(
            onClick = { tile.type = GameTileType.Wall },
            enabled = tile.canTileBeAWall(),
            icon = {
                val icon =
                    remember { ResourceReader(GameTileType.Wall.getResource()).getContentAsImage().asImageBitmap() }

                Image(
                    icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(if (tile.type == GameTileType.Wall) 0.5f else 0f) }),
                    modifier = Modifier.size(40.dp)
                )
            },
            text = { Text(HamsterString.getForGameTileType(GameTileType.Wall)) }
        )

        IconButtonWithText(
            onClick = { tile.type = GameTileType.Floor },
            icon = {
                val icon =
                    remember { ResourceReader(GameTileType.Floor.getResource()).getContentAsImage().asImageBitmap() }

                Image(
                    icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(if (tile.type == GameTileType.Floor) 0.5f else 0f) }),
                    modifier = Modifier.size(40.dp)
                )
            },
            text = { Text(HamsterString.getForGameTileType(GameTileType.Floor)) }
        )
    }

    Button(
        onClick = { EditorState.setTileOfStartingHamster(tile) },
        enabled = !tile.hasHamsterContent() && !tile.blocked,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text(HamsterString.get("editor.side.edit.tile.set.hamster.start"))
    }

    TextFieldForNumbers(
        value = tile.grainCount,
        onValueChanged = {
            if (it >= 0) {
                tile.setGrainCount(it)
            }
        },
        label = { Text(HamsterString.get("editor.side.edit.tile.grain.count")) },
        enabled = !tile.blocked
    )
}
