package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.internal.model.hamster.EditableHamster
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Panel showing different tools to edit the currently selected tile which should get edited.
 *
 * @param onClose Gets called if the user clicks on the "Close" button.
 * @param modifier Modifiers applied to the underlying [Column].
 */
@Composable
fun EditPanel(onClose: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.background(Color.Cyan)) {
        // TODO: Make whole panel prettier
        Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
            Text("CLOSE")
        }

        val editedTile by EditorState.editedTile
        var startingHamster by EditorState.startingHamster

        editedTile?.let {
            val tile = it.tile
            var textFieldValue by remember(tile) { mutableStateOf(tile.grainCount.toString()) }

            TextField(
                value = textFieldValue,
                onValueChange = { value ->
                    textFieldValue = value
                    val newGrainCount: Int? = value.toIntOrNull()
                    if (newGrainCount != null && newGrainCount >= 0) {
                        tile.setGrainCount(newGrainCount)
                    }
                },
                label = { Text("Grain Count") },
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

            Button(onClick = {
                if (startingHamster != null) {
                    startingHamster!!.currentTile.removeContent(startingHamster!!)
                }

                val hamster = EditableHamster(tile, Direction.East, 0)
                startingHamster = hamster
                tile.addContent(hamster)

            }, enabled = !tile.hasHamsterContent() && !tile.blocked) {
                Text("SET HAMSTER START")
            }

            tile.tileContent.forEach { content ->
                // TODO: Add edit boxes for the tile content.
                Divider(Modifier.padding(16.dp))
                Text("EDIT PANEL FOR ${content.javaClass}")
            }
        }

    }
}
