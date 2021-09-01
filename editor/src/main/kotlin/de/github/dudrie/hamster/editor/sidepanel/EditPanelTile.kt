package de.github.dudrie.hamster.editor.sidepanel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.application.EditorState
import de.github.dudrie.hamster.editor.components.EditPanelTileTypeButton
import de.github.dudrie.hamster.editor.model.EditableGameTile
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.components.TextFieldForNumbers

/**
 * Edit panel that allows to change the configuration of the given [tile].
 */
@Composable
fun EditPanelTile(tile: EditableGameTile) {
    Row(Modifier.padding(bottom = 16.dp)) {
        EditPanelTileTypeButton(
            type = GameTileType.Wall,
            onClick = { tile.type = it },
            isSelected = { tile.type == it },
            enabled = tile.canTileBeAWall()
        )

        EditPanelTileTypeButton(
            type = GameTileType.Floor,
            onClick = { tile.type = it },
            isSelected = { tile.type == it }
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
