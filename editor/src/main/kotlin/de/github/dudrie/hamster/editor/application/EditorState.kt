package de.github.dudrie.hamster.editor.application

import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Richtung
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.datatypes.SpielOrt
import de.github.dudrie.hamster.editor.model.*
import de.github.dudrie.hamster.editor.model.builder.EditableTerritoryBuilder
import de.github.dudrie.hamster.editor.tools.TileTool
import de.github.dudrie.hamster.file.model.InitialHamsterData
import de.github.dudrie.hamster.file.model.InitialTerritoryData
import de.github.dudrie.hamster.importer.helpers.parseJson
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.name

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
            return hamster != null && territory.value.abmessungen.isLocationInside(hamster.tile.location)
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
     * Sets the tile to meter scaling of the currently edited territory.
     */
    fun setTerritoryTileToMeterScaling(scaling: Double) {
        territory.value.feldZuMeterSkalierung = scaling
    }

    /**
     * Saves the current territory to the file at the given [path].
     */
    fun saveToFile(path: Path): IOOperationResult {
        val territoryData = createDataToSave()

        var filePath = path
        if (!filePath.name.endsWith(".json")) {
            filePath = filePath.resolveSibling("${path.fileName}.json")
        }

        return try {
            val writer = Files.newBufferedWriter(filePath, Charsets.UTF_8)
            writer.use {
                writer.write(territoryData.toJson())
            }
            IOOperationResult()
        } catch (e: IOException) {
            IOOperationResult(e)
        }
    }

    /**
     * Loads a territory from the file at the given [path].
     */
    fun loadFromFile(path: Path): IOOperationResult {
        return try {
            if (!path.name.endsWith(".json")) {
                throw IOException("Only json files are supported.")
            }

            val reader = Files.newBufferedReader(path, Charsets.UTF_8)
            reader.use {
                val data = parseJson<InitialTerritoryData>(reader.readText())
                val builder = EditableTerritoryBuilder(data.territorySize, data.tileToMeterScaling)

                data.getAllSpecialTiles().forEach { tile ->
                    builder.addSpecialTile(tile)
                }

                territory.value = builder.buildEditableTerritory()
                val hamsterTile = territory.value.holeFeldBei(data.initialHamster.location)

                startingHamster.value =
                    EditableHamster(hamsterTile, data.initialHamster.direction, data.initialHamster.grainCount)
                hamsterTile.addContent(startingHamster.value!!)
                resetTools()
            }
            IOOperationResult()
        } catch (e: IOException) {
            IOOperationResult(e)
        }
    }

    /**
     * Fills all tiles around the territory with [walls][GameTileType.Wall].
     *
     * However, only tiles that are empty are being replaced with walls
     */
    fun surroundTerritoryWithWalls() {
        val territory = territory.value
        val (columnCount, rowCount) = territory.abmessungen

        for (column in 0 until columnCount) {
            val locationTop = SpielOrt(column, 0)
            val locationBottom = SpielOrt(column, rowCount - 1)
            territory.holeFeldBei(locationTop).makeWallIfPossible()
            territory.holeFeldBei(locationBottom).makeWallIfPossible()
        }

        for (row in 1 until rowCount - 1) {
            val locationLeft = SpielOrt(0, row)
            val locationRight = SpielOrt(columnCount - 1, row)
            territory.holeFeldBei(locationLeft).makeWallIfPossible()
            territory.holeFeldBei(locationRight).makeWallIfPossible()
        }
    }

    /**
     * Converts the data in the [EditorState] to an [InitialTerritoryData] object.
     *
     * This object can then be used to save the data as JSON to a file.
     */
    private fun createDataToSave(): InitialTerritoryData {
        val hamster = startingHamster.value
        val territory = territory.value
        val size = territory.abmessungen

        require(hamster != null) { "The starting hamster must be present on the territory." }

        val hamsterData = InitialHamsterData(
            location = hamster.tile.location,
            direction = hamster.direction,
            grainCount = hamster.grainCount
        )
        val territoryData = EditableTerritoryData(
            territorySize = size,
            initialHamster = hamsterData,
            tileToMeterScaling = territory.feldZuMeterSkalierung
        )
        size.getAllLocationsInside().forEach { location ->
            val tile = territory.holeFeldBei(location)

            if (tile.grainCount > 0) {
                territoryData.addGrainTile(tile)
            }

            if (tile.type == GameTileType.Wall) {
                territoryData.addWallTile(location)
            }
        }
        return territoryData
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
    private fun getDefaultHamster(tile: EditableGameTile) = EditableHamster(tile, Richtung.Osten, 0)

    /**
     * Creates and returns a default empty [EditableTerritory].
     */
    private fun getDefaultTerritory(): EditableTerritory = EditableTerritory(Size(5, 3), 0.5)

}
