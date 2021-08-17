package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ResString
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
    // TODO: Clean up composable & file
    Surface(modifier = modifier, elevation = 16.dp) {
        // TODO: Make column scrollable?!
        Column(Modifier.padding(8.dp)) {
            // TODO: Make whole panel prettier
            // TODO: Add "validation" & user feedback (error props, ...)
            IconButton(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
                Text("X")
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
                    enabled = !tile.blocked,
                    maxLines = 1
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

                tile.tileContent.forEach { content ->
                    Divider(Modifier.padding(16.dp))

                    if (content is EditableHamster) {
                        Text("EDIT A HAMSTER", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                        var dropDownExpanded by remember(content) { mutableStateOf(false) }
                        var hamsterGrainCountTextFieldValue by remember(content) { mutableStateOf(content.grainCount.toString()) }
                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                            OutlinedButton(onClick = {
                                dropDownExpanded = true
                            }) {
                                Text(ResString.getForDirection(content.direction))

                                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                            }
                            DropdownMenu(expanded = dropDownExpanded, onDismissRequest = { dropDownExpanded = false }) {
                                DropdownMenuItem({
                                    content.setDirection(Direction.North)
                                    dropDownExpanded = false
                                }) { Text(ResString.getForDirection(Direction.North)) }
                                DropdownMenuItem({
                                    content.setDirection(Direction.East)
                                    dropDownExpanded = false
                                }) { Text(ResString.getForDirection(Direction.East)) }
                                DropdownMenuItem({
                                    content.setDirection(Direction.South)
                                    dropDownExpanded = false
                                }) { Text(ResString.getForDirection(Direction.South)) }
                                DropdownMenuItem({
                                    content.setDirection(Direction.West)
                                    dropDownExpanded = false
                                }) { Text(ResString.getForDirection(Direction.West)) }
                            }
                        }

                        TextField(
                            value = hamsterGrainCountTextFieldValue,
                            onValueChange = { value ->
                                hamsterGrainCountTextFieldValue = value
                                val newGrainCount: Int? = value.toIntOrNull()
                                if (newGrainCount != null && newGrainCount >= 0) {
                                    content.setGrainCount(newGrainCount)
                                }
                            },
                            label = { Text("Grain Count") },
                            maxLines = 1
                        )
                        Text("GRAIN COUNT OF HAMSTER: ${content.grainCount}")
                    }
                }
            }
        }
    }
}
