package de.gothub.dudrie.hamster.importer.data

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.gothub.dudrie.hamster.importer.helpers.parseJson
import de.gothub.dudrie.hamster.importer.helpers.stringifyJson

class InitialTerritoryData(val territorySize: Size, val initialHamster: HamsterData) {

    companion object {
        fun fromJson(json: String): InitialTerritoryData = parseJson(json)
    }

    private val specialTiles: MutableMap<Location, TileData> = mutableMapOf()

    fun addWallTile(location: Location) {
        require(isInside(location)) { "Location $location is not inside the territory. Territory size: $territorySize." }
        checkIfLocationAlreadyHasSpecialTile(location)

        specialTiles[location] = TileData(location = location, tileType = GameTileType.Wall)
    }

    fun addGrains(grainCount: Int, location: Location) {
        require(isInside(location)) { "Location $location is not inside the territory. Territory size: $territorySize." }
        require(!hasWallAtLocation(location)) { "Grains must not be put on a wall tile. Location: $location" }
        require(grainCount > 0) { "Grain count must be 1 or greater. Grain count: $grainCount" }

        specialTiles[location] = TileData(location = location, grainCount = grainCount)
    }

    fun getSpecialTiles(): List<TileData> = specialTiles.toList().map { it.second }

    fun toJson(): String = stringifyJson(this)

    private fun isInside(location: Location): Boolean = territorySize.isLocationInside(location)

    private fun hasWallAtLocation(location: Location): Boolean = specialTiles[location]?.tileType == GameTileType.Wall

    private fun checkIfLocationAlreadyHasSpecialTile(location: Location) {
        require(!specialTiles.containsKey(location)) { "Adding this tile would override the already present special tile at location $location." }
    }
}
