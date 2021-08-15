package de.github.dudrie.hamster.importer.data

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.importer.helpers.parseJson
import de.github.dudrie.hamster.importer.helpers.stringifyJson
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Data used to generate the initial [Territory][de.github.dudrie.hamster.external.model.Territory].
 *
 * @param territorySize [Size] of the territory.
 * @param initialHamster Data to generate the initial, default [Hamster][de.github.dudrie.hamster.external.model.Hamster].
 */
class InitialTerritoryData(val territorySize: Size, val initialHamster: HamsterData) {

    /**
     * Exposes function to instantiate the [InitialTerritoryData] from JSON.
     */
    companion object {
        /**
         * Create an [InitialTerritoryData] from [json] data.
         */
        fun fromJson(json: String): InitialTerritoryData = parseJson(json)
    }

    /**
     * [MutableMap] which contains all data of special tiles of this territory.
     */
    private val specialTiles: MutableMap<Location, TileData> = mutableMapOf()

    /**
     * Add a wall [tile][TileData] at the given [location].
     *
     * The [location] must be inside the territory and must not already be a special tile.
     */
    fun addWallTile(location: Location) {
        require(isInside(location)) { "Location $location is not inside the territory. Territory size: $territorySize." }
        checkIfLocationAlreadyHasSpecialTile(location)

        specialTiles[location] = TileData(location = location, tileType = GameTileType.Wall)
    }

    /**
     * Add [grainCount] grains onto the given [location].
     *
     * The [location] must be inside the territory, must not be a wall (or similar blocked tile) and [grainCount] must be zero or greater.
     */
    fun addGrains(grainCount: Int, location: Location) {
        require(isInside(location)) { "Location $location is not inside the territory. Territory size: $territorySize." }
        require(!hasWallAtLocation(location)) { "Grains must not be put on a wall tile. Location: $location" }
        require(grainCount > 0) { "Grain count must be 1 or greater. Grain count: $grainCount" }

        specialTiles[location] = TileData(location = location, grainCount = grainCount)
    }

    /**
     * Returns a [List] with the [TileData] of all special tiles.
     *
     * This [List] does **NOT** include a [TileData] for every tile in the territory but only the special tiles (ie tiles which are not empty floors).
     */
    fun getAllSpecialTiles(): List<TileData> = specialTiles.toList().map { it.second }

    /**
     * Returns the JSON representation of this object.
     */
    fun toJson(): String = stringifyJson(this)

    /**
     * Checks if the [location] is inside the territory.
     */
    private fun isInside(location: Location): Boolean = territorySize.isLocationInside(location)

    /**
     * Checks if the territory has a wall at the [location].
     */
    private fun hasWallAtLocation(location: Location): Boolean = specialTiles[location]?.tileType == GameTileType.Wall

    /**
     * Checks if the territory already has a special tile at the [location].
     */
    private fun checkIfLocationAlreadyHasSpecialTile(location: Location) {
        require(!specialTiles.containsKey(location)) { "Adding this tile would override the already present special tile at location $location." }
    }
}
