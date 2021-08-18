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
     * @see resetTools
     * @see getDefaultTerritory
     */
    fun resetTerritory() {
        resetTools()
        startingHamster.value = null
        territory.value = getDefaultTerritory()
    }

    /**
     * Sets the size of the territory to the [newSize].
     *
     * All tiles will be kept if they are still inside the [newSize]. Empty slots will be filled with default floor tiles.
     *
     * While this reset the [editedTile] and the [selectedTool] properties it does **NOT** reset the [startingHamster]. If the [startingHamster] is outside the territory it will get replaced with all its data if the user selects a new tile.
     *
     * @see EditableTerritory.setSize
     */
    fun setTerritorySize(newSize: Size) {
        territory.value.setSize(newSize)
        resetTools()
    }

    /**
     * Resets [selectedTool] and [editedTile] properties.
     */
    private fun resetTools() {
        selectedTool.value = null
        editedTile.value = null
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
