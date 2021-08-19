package de.github.dudrie.hamster.editor.application

import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.model.EditableGameTile
import de.github.dudrie.hamster.editor.model.EditableHamster
import de.github.dudrie.hamster.editor.model.EditableTerritory
import de.github.dudrie.hamster.editor.tools.TileTool
import de.github.dudrie.hamster.file.model.InitialHamsterData
import de.github.dudrie.hamster.file.model.InitialTerritoryData
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import java.io.FileWriter
import java.nio.file.Path

/**
 * State of the editor.
 */
object EditorState {
    /**
     * Maximum amount of columns or rows of a territory.
     *
     * The maximums size is: `maxColumnAndRowCount` x `maxColumnAndRowCount`.
     */
    const val maxColumnAndRowCount = 30

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
     * Has the editor a starting hamster present and is it visible on the current territory?
     */
    val hasStartingHamster: Boolean
        get() {
            val hamster = startingHamster.value
            return hamster != null && territory.value.territorySize.isLocationInside(hamster.currentTile.location)
        }

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
     * Saves the current territory to the file at the given [path].
     */
    fun saveToFile(path: Path) {
        val hamster = startingHamster.value
        val territory = territory.value
        val size = territory.territorySize

        // TODO: Don't allow the creation without default hamster -> User feedback earlier
        require(hamster != null) { "The starting hamster must be present on the territory." }

        val hamsterData = InitialHamsterData(
            location = hamster.currentTile.location,
            direction = hamster.direction,
            grainCount = hamster.grainCount
        )
        val territoryData = InitialTerritoryData(territorySize = size, initialHamster = hamsterData)

        size.getAllLocationsInside().forEach { location ->
            val tile = territory.getTileAt(location)

            if (tile.grainCount > 0) {
                territoryData.addGrains(tile.grainCount, location)
            }

            if (tile.type == GameTileType.Wall) {
                territoryData.addWallTile(location)
            }
        }

        var filePath = path
        if (!filePath.endsWith(".json")) {
            filePath = filePath.resolveSibling("${path.fileName}.json")
        }

        val writer = FileWriter(filePath.toFile(), Charsets.UTF_8)
        writer.use {
            writer.write(territoryData.toJson())
        }

        // TODO: Catch exceptions!

        // TODO: Inform user, writing was successful!
    }

    /**
     * Loads a territory from the file at the given [path].
     */
    fun loadFromFile(path: Path) {
        // TODO: Check extension!
        TODO("Not yet implemented")
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
