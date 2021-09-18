package de.github.dudrie.hamster.importer.helpers

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.execptions.InitialTerritoryInvalidException
import de.github.dudrie.hamster.file.model.TileData
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Builder used to build the initial [GameTerritory].
 *
 * @param territorySize [Size] of the territory that will be generated.
 */
open class TerritoryBuilder(val territorySize: Size, val tileToMeterScaling: Double) {
    /**
     * Tiles of the tutorial.
     */
    private val tiles: MutableList<GameTile> = mutableListOf()

    /**
     * Builds the [GameTerritory] based upon the information in the builder and returns it.
     *
     * If there are locations without a tile inside [territorySize] those will be filled with empty floor tiles before the territory gets generated.
     */
    fun buildGameTerritory(): GameTerritory {
        fillEmptyTiles()

        return GameTerritory(size = territorySize, tiles = tiles, tileToMeterScaling = tileToMeterScaling)
    }

    /**
     * Adds a [specialTile] to the list of [tiles] of this builder.
     *
     * The [specialTile]  must conform to the following condition:
     * - It must be inside the [territorySize].
     * - There must not already be a special tile at the location of the [specialTile].
     * - It must not be a [wall][GameTileType.Wall] with grains on it.
     */
    fun addSpecialTile(specialTile: TileData) {
        val location = specialTile.location

        if (!territorySize.isLocationInside(location)) {
            throw InitialTerritoryInvalidException("The tile at $location is not inside the territory boundaries.")
        }
        if (hasLocationATile(specialTile.location)) {
            throw InitialTerritoryInvalidException("There already are information for a special tile at ${specialTile.location}.")
        }
        if (specialTile.tileType == GameTileType.Wall && specialTile.grainCount > 0) {
            throw InitialTerritoryInvalidException("The tile at ${specialTile.location} would have a wall with grains on it. This is NOT allowed.")
        }

        tiles += GameTile(location = location, type = specialTile.tileType, grainCount = specialTile.grainCount)
    }

    /**
     * Returns a copy of the list of all tiles.
     */
    protected fun getAllTiles(): List<GameTile> = tiles.toList()

    /**
     * Adds an empty floor tile at the [location].
     */
    private fun addDefaultTile(location: Location) {
        tiles += GameTile(location = location, type = GameTileType.Floor)
    }

    /**
     * Fills all locations with the [territorySize] which have no tiles yet with the default tiles.
     *
     * @see addDefaultTile
     */
    protected fun fillEmptyTiles() {
        for (location in territorySize.getAllLocationsInside()) {
            if (!hasLocationATile(location)) {
                addDefaultTile(location)
            }
        }
    }

    /**
     * Checks if the builder already as information for a tile at the [location].
     */
    private fun hasLocationATile(location: Location): Boolean = tiles.find { it.location == location } != null
}
