package de.github.dudrie.hamster.internal.model.territory

import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.datatypes.SpielOrt
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent

/**
 * Holds the state of the game's territory.
 *
 * @param size [Size] of the [GameTerritory]
 * @param tiles [List] of all [GameTiles][GameTile] of the [GameTerritory]. Every [SpielOrt] that is inside the [size] must have a [GameTile] in this [List].
 * @param tileToMeterScaling The amount of meters a single tile represents in the territory.
 */
class GameTerritory(val size: Size, private val tiles: List<GameTile>, val tileToMeterScaling: Double) {

    init {
        require(tiles.size == size.columnCount * size.rowCount) { "Count of tiles (${tiles.size}) does not match the size size." }
    }

    /**
     * Gets and returns the [GameTile] at the given [SpielOrt].
     *
     * There has to be a [GameTile] at the [location].
     *
     * @param location [SpielOrt] to get the tile at.
     */
    fun getTileAt(location: SpielOrt): GameTile {
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
     * Checks whether the given [SpielOrt] is inside this territory.
     *
     * @param location [SpielOrt] to check.
     *
     * @return Is the [location] inside this territory?
     */
    fun isLocationInside(location: SpielOrt): Boolean {
        return size.isLocationInside(location)
    }

    /**
     * Returns the number the next created hamster should get.
     */
    fun getNextHamsterNumber(): Int = getHamsterCount() + 1

    /**
     * Returns `true` if the territory has more than one hamster.
     */
    fun hasMultipleHamsters(): Boolean = getHamsterCount() > 1

    /**
     * Returns the current count of hamsters in the territory.
     */
    private fun getHamsterCount(): Int {
        return tiles.flatMap { it.tileContent }.filterIsInstance(HamsterTileContent::class.java).size
    }
}
