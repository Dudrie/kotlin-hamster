package de.github.dudrie.hamster.ui.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.internal.model.hamster.EditableHamster
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

    /**
     * Hamster which contains the information about the initially spawned hamster in a game.
     */
    val startingHamster = mutableStateOf<EditableHamster?>(null)

    /**
     * Sets the tile of the [startingHamster] to the given one.
     *
     * If there is a [startingHamster] it gets moved to the new [tile] keeping all its properties. Otherwise, a new [startingHamster] gets created.
     */
    fun setTileOfStartingHamster(tile: EditableGameTile) {
        val hamster = startingHamster.value ?: getDefaultHamster(tile)

        hamster.setTile(tile)
        startingHamster.value = hamster
    }

    /**
     * Returns a default hamster.
     */
    private fun getDefaultHamster(tile: EditableGameTile) = EditableHamster(tile, Direction.East, 0)
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
