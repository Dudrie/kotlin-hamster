package de.github.dudrie.hamster.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.model.EditableGameTile
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.components.IconButtonWithText
import de.github.dudrie.hamster.ui.components.TextFieldForNumbers
import de.github.dudrie.hamster.ui.theme.GameTheme

/**
 * Edit panel that allows to change the configuration of the given [tile].
 */
@Composable
fun EditPanelTile(tile: EditableGameTile) {
    TextFieldForNumbers(
        value = tile.grainCount,
        onValueChanged = {
            if (it > 0) {
                tile.setGrainCount(it)
            }
        },
        label = { Text("GRAIN COUNT") },
        enabled = !tile.blocked
    )

    Row(Modifier.padding(vertical = 16.dp)) {
        IconButtonWithText(
            onClick = { tile.type = GameTileType.Wall },
            enabled = tile.canTileBeAWall(),
            icon = {
                val icon = remember { ResourceReader(R.images.wall).getContentAsImage().asImageBitmap() }

                Image(
                    icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(if (tile.type == GameTileType.Wall) 0.5f else 0f) }),
                    modifier = Modifier.size(40.dp)
                )
            },
            text = { Text("WALL") }
        )

        IconButtonWithText(
            onClick = { tile.type = GameTileType.Floor },
            //enabled = tile.type != GameTileType.Floor,
            icon = {
                Box(
                    Modifier.background(if (tile.type == GameTileType.Floor) GameTheme.colors.floor else Color.Gray)
                        .border(1.dp, Color.Black)
                        .size(40.dp)
                )
            },
            text = { Text("FLOOR") }
        )
    }

    Button(
        onClick = { EditorState.setTileOfStartingHamster(tile) },
        enabled = !tile.hasHamsterContent() && !tile.blocked
    ) {
        Text("SET HAMSTER START")
    }
}
