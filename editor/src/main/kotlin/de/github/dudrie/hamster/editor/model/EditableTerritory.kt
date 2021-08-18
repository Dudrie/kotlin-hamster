package de.github.dudrie.hamster.editor.model

import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.interfaces.AbstractTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Holds the data and helpers necessary to edit a territory.
 *
 * @param initialSize Initial size of the territory.
 */
class EditableTerritory(initialSize: Size) : AbstractTerritory() {

    private val territorySizeState = mutableStateOf(initialSize)

    override val territorySize: Size
        get() = territorySizeState.value

    /**
     * Tiles currently present in the territory.
     */
    private val tiles = mutableListOf<EditableGameTile>()

    init {
        for (location in territorySize.getAllLocationsInside()) {
            tiles.add(createDefaultTile(location))
        }
    }

    /**
     * Returns the tile at the given [location].
     *
     * @throws NoSuchElementException If there is no tile at the [location].
     */
    override fun getTileAt(location: Location): EditableGameTile = tiles.first { it.location == location }

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
    private fun getTileAtOrNull(location: Location): EditableGameTile? {
        return try {
            getTileAt(location)
        } catch (e: NoSuchElementException) {
            null
        }
    }

    /**
     * Creates and returns an empty floor tile.
     */
    private fun createDefaultTile(location: Location): EditableGameTile =
        EditableGameTile(location, GameTileType.Floor, 0)
}
