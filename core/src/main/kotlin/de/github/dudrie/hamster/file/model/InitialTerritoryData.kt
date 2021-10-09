package de.github.dudrie.hamster.file.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.importer.helpers.parseJson
import de.github.dudrie.hamster.importer.helpers.stringifyJson
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Data used to generate the initial territory.
 *
 * @param territorySize [Size] of the territory.
 * @param initialHamster Data to generate the initial, default hamster.
 * @param tileToMeterScaling The amount of meters a single tile represents in the territory.
 */
open class InitialTerritoryData(
    val territorySize: Size,
    val initialHamster: InitialHamsterData,
    val tileToMeterScaling: Double
) {

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

        specialTiles[location] =
            TileData(location = location, tileType = GameTileType.Wall, hideGrainCount = false, grainCount = 0)
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
    protected fun isInside(location: Location): Boolean = territorySize.isLocationInside(location)

    /**
     * Checks if the territory has a wall at the [location].
     */
    protected fun hasWallAtLocation(location: Location): Boolean = specialTiles[location]?.tileType == GameTileType.Wall

    /**
     * Sets the special tile at the [location] the given [tile].
     */
    protected fun setSpecialTileAt(location: Location, tile: TileData) {
        specialTiles[location] = tile
    }

    /**
     * Returns the special tile at the [location].
     *
     * If there is no special tile a default tile will be generated and returned.
     */
    protected fun getSpecialTileAt(location: Location): TileData = specialTiles[location] ?: TileData(
        location = location,
        grainCount = 0,
        tileType = GameTileType.Floor,
        hideGrainCount = false
    )

    /**
     * Checks if the territory already has a special tile at the [location].
     */
    private fun checkIfLocationAlreadyHasSpecialTile(location: Location) {
        require(!specialTiles.containsKey(location)) { "Adding this tile would override the already present special tile at location $location." }
    }
}
