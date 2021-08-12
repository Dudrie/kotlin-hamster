package de.github.dudrie.hamster.importer

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.execptions.InitialTerritoryInvalidException
import de.github.dudrie.hamster.importer.data.TileData
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

class TerritoryBuilder(private val territorySize: Size) {
    private val tiles: MutableList<GameTile> = mutableListOf()

    fun build(): GameTerritory {
        fillEmptyTiles()

        return GameTerritory(territorySize, tiles)
    }

    fun addSpecialTile(specialTile: TileData) {
        val location = specialTile.location

        if (!territorySize.isLocationInside(location)) {
            throw InitialTerritoryInvalidException("The tile at $location is not inside the territory boundaries.")
        }
        if (specialTile.tileType == GameTileType.Wall && specialTile.grainCount > 0) {
            throw InitialTerritoryInvalidException("The tile at ${specialTile.location} would have a wall with grains on it. This is NOT allowed.")
        }

        tiles += GameTile(location = location, type = specialTile.tileType, grainCount = specialTile.grainCount)
    }

    private fun addDefaultTile(location: Location) {
        tiles += GameTile(location = location, type = GameTileType.Floor)
    }

    private fun fillEmptyTiles() {
        for (location in territorySize.getAllLocationsInside()) {
            if (!hasLocationATile(location)) {
                addDefaultTile(location)
            }
        }
    }

    private fun hasLocationATile(location: Location): Boolean = tiles.find { it.location == location } != null
}
