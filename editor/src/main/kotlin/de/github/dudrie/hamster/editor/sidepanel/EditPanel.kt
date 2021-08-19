package de.github.dudrie.hamster.editor.sidepanel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.application.EditorState

/**
 * Panel showing different tools to edit the currently selected tile which should get edited.
 *
 * @param onClose Gets called if the user clicks on the "Close" button.
 * @param modifier Modifiers applied to the underlying [Column].
 */
@Composable
fun EditPanel(onClose: () -> Unit, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, elevation = 16.dp) {
        Column(Modifier.padding(8.dp)) {
            // TODO: Add "validation" & user feedback (error props, ...)
            IconButton(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
                Icon(Icons.Rounded.Close, contentDescription = null)
            }

            val editedTile by EditorState.editedTile

            editedTile?.let {
                EditPanelTile(it.tile)

                it.tile.tileContent.forEach { content ->
                    Divider(Modifier.padding(16.dp))

                    EditPanelTileContent(content)
                }
            }
        }
    }
}
