package de.github.dudrie.hamster.internal.model.territory

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size

/**
 * Holds the state of the game's territory.
 *
 * @param size [Size] of the [GameTerritory]
 * @param tiles [List] of all [GameTiles][GameTile] of the [GameTerritory]. Every [Location] that is inside the [size] must have a [GameTile] in this [List].
 */
class GameTerritory(val size: Size, private val tiles: List<GameTile>, val tileToMeterScaling: Double) {

    init {
        require(tiles.size == size.columnCount * size.rowCount) { "Count of tiles (${tiles.size}) does not match the size size." }
    }

    /**
     * Gets and returns the [GameTile] at the given [Location].
     *
     * There has to be a [GameTile] at the [location].
     *
     * @param location [Location] to get the tile at.
     */
    fun getTileAt(location: Location): GameTile {
        val tile = tiles.find { it.location == location }
        require(tile != null) { "There is not a tile at location $location" }
        return tile
    }

    /**
     * Checks whether the given [GameTile] is inside this territory.
     *
     * @param tile [GameTile] to check.
     *
     * @return Is the [tile] inside this territory?
     */
    fun isTileInside(tile: GameTile): Boolean {
        return this.isLocationInside(tile.location)
    }

    /**
     * Checks whether the given [Location] is inside this territory.
     *
     * @param location [Location] to check.
     *
     * @return Is the [location] inside this territory?
     */
    fun isLocationInside(location: Location): Boolean {
        return size.isLocationInside(location)
    }
}
