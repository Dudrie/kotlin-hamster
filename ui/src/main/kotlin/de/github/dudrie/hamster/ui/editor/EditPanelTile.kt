package de.github.dudrie.hamster.ui.editor

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import de.github.dudrie.hamster.internal.model.territory.EditableGameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.components.TextFieldForNumbers

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

    Button(
        onClick = { tile.type = GameTileType.Wall },
        enabled = tile.type != GameTileType.Wall && !tile.hasHamsterContent() && tile.grainCount == 0
    ) {
        Text("MAKE WALL")
    }
    Button(onClick = { tile.type = GameTileType.Floor }, enabled = tile.type != GameTileType.Floor) {
        Text("MAKE FLOOR")
    }

    Button(
        onClick = { EditorState.setTileOfStartingHamster(tile) },
        enabled = !tile.hasHamsterContent() && !tile.blocked
    ) {
        Text("SET HAMSTER START")
    }
}
