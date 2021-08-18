package de.github.dudrie.hamster.editor

import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.model.EditableGameTile
import de.github.dudrie.hamster.editor.model.EditableHamster
import de.github.dudrie.hamster.editor.model.EditableTerritory
import de.github.dudrie.hamster.editor.tools.TileTool

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
     */
    val territory = mutableStateOf(getDefaultTerritory())

    /**
     * Currently selected tool for manipulating tiles in the editor.
     */
    val selectedTool = mutableStateOf<TileTool?>(null)

    /**
     * Hamster which contains the information about the initially spawned hamster in a game.
     */
    private val startingHamster = mutableStateOf<EditableHamster?>(null)

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
     * Returns the currently selected tool.
     */
    fun getCurrentlySelectedTool(): TileTool? = selectedTool.value

    /**
     * Resets the [territory] to be a default empty territory.
     *
     * This also resets the [editedTile], [selectedTool] and the [startingHamster].
     *
     * @see getDefaultTerritory
     */
    fun resetTerritory() {
        editedTile.value = null
        startingHamster.value = null
        selectedTool.value = null
        territory.value = getDefaultTerritory()
    }

    /**
     * Returns a default hamster.
     */
    private fun getDefaultHamster(tile: EditableGameTile) = EditableHamster(tile, Direction.East, 0)

    /**
     * Creates and returns a default empty [EditableTerritory].
     */
    private fun getDefaultTerritory(): EditableTerritory = EditableTerritory(Size(5, 3))
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
