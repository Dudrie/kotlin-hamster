package de.github.dudrie.hamster.ui.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.internal.model.territory.EditableGameTile
import de.github.dudrie.hamster.ui.application.EditorTerritoryLocal

/**
 * State of the editor.
 */
object EditorState {
    /**
     * Currently edited tile. If no tile is edited this is `null`.
     */
    val editedTile = mutableStateOf<EditedTile?>(null)

    /**
     * Territory that currently gets edited.
     *
     * @see EditorTerritoryLocal
     */
    val territory: AbstractEditableTerritory
        @Composable
        get() = EditorTerritoryLocal.current

}

/**
 * Information about a [tile] that gets currently edited by the editor.
 *
 * @param tile Tile that gets edited.
 */
data class EditedTile(val tile: EditableGameTile) {
    /**
     * [Location] of the [tile] that gets edited.
     */
    val location: Location = tile.location
}
