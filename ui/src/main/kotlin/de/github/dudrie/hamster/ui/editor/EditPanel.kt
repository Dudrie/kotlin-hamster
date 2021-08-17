package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
            Text("CLOSE")
        }

        val editedTile by EditorState.editedTile

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
            )

            Button(onClick = { tile.type = GameTileType.Wall }) {
                Text("MAKE WALL")
            }
            Button(onClick = { tile.type = GameTileType.Floor }) {
                Text("MAKE FLOOR")
            }

            // TODO: Add button which spawns a Hamster
            //       This requires a new TileContent: EditableHamster.
            //       Which itself should add an abstraction between GameHamster and GameTileContent: HamsterTileContent, because there is probably no visual difference between a GameHamster and an EditableHamster.
        }

    }
}
