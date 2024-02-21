package de.github.dudrie.hamster.editor.model

import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.datatypes.SpielOrt
import de.github.dudrie.hamster.interfaces.AbstraktesTerritorium
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Holds the data and helpers necessary to edit a territory.
 *
 * @param initialSize Initial size of the territory.
 */
class EditableTerritory(initialSize: Size, initialTileToMeterScaling: Double) : AbstraktesTerritorium() {

    private val territorySizeState = mutableStateOf(initialSize)

    private val tileToMeterScalingState = mutableStateOf(initialTileToMeterScaling)

    /**
     * The [Size] of the territory.
     */
    override val abmessungen: Size
        get() = territorySizeState.value

    /**
     * The scaling how many meters a tile in the territory represents.
     */
    override var feldZuMeterSkalierung: Double
        get() = tileToMeterScalingState.value
        set(value) {
            tileToMeterScalingState.value = value
        }

    /**
     * Tiles currently present in the territory.
     */
    private val tiles = mutableListOf<EditableGameTile>()

    constructor(initialSize: Size, initialTileToMeterScaling: Double, tiles: List<EditableGameTile>) : this(
        initialSize,
        initialTileToMeterScaling
    ) {
        this.tiles.clear()
        this.tiles.addAll(tiles)
    }

    init {
        for (location in abmessungen.getAllLocationsInside()) {
            tiles.add(createDefaultTile(location))
        }
    }

    /**
     * Returns the tile at the given [ort].
     *
     * @throws NoSuchElementException If there is no tile at the [ort].
     */
    override fun holeFeldBei(ort: SpielOrt): EditableGameTile = tiles.first { it.location == ort }

    /**
     * Sets the size of the territory.
     *
     * Old tiles will get kept if they are still in the new size boundaries. Tiles outside the boundaries are discarded. If the new size if larger than the old one the "empty" locations will get filled up with empty floor tiles.
     *
     * @see createDefaultTile
     */
    fun setSize(newSize: Size) {
        val newTiles = mutableListOf<EditableGameTile>()

        for (location in newSize.getAllLocationsInside()) {
            newTiles.add(getTileAtOrNull(location) ?: createDefaultTile(location))
        }

        tiles.clear()
        tiles.addAll(newTiles)
        territorySizeState.value = newSize
    }

    /**
     * Returns the tile at the [location].
     *
     * If there is no tile at the given [location] `null` is returned instead.
     */
    private fun getTileAtOrNull(location: SpielOrt): EditableGameTile? {
        return try {
            holeFeldBei(location)
        } catch (e: NoSuchElementException) {
            null
        }
    }

    /**
     * Creates and returns an empty floor tile.
     */
    private fun createDefaultTile(location: SpielOrt): EditableGameTile =
        EditableGameTile(location = location, type = GameTileType.Floor, grainCount = 0, hideGrainCount = false)
}
