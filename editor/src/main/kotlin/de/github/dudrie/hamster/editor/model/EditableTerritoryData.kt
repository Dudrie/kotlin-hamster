package de.github.dudrie.hamster.editor.model

import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.file.model.InitialHamsterData
import de.github.dudrie.hamster.file.model.InitialTerritoryData
import de.github.dudrie.hamster.file.model.TileData

/**
 * Holds the data of a territory with additional functions which allow changing the data of the territory.
 */
class EditableTerritoryData(
    territorySize: Size,
    initialHamster: InitialHamsterData,
    tileToMeterScaling: Double
) : InitialTerritoryData(territorySize, initialHamster, tileToMeterScaling) {

    /**
     * Adds the given [tile] as special tile to the territory.
     */
    fun addGrainTile(tile: EditableGameTile) {
        val location = tile.location
        require(isInside(location)) { "Location $location is not inside the territory. Territory size: $territorySize." }
        require(!hasWallAtLocation(location)) { "Grains must not be put on a wall tile. Location: $location" }
        require(tile.grainCount > 0) { "Grain count must be 1 or greater. Grain count: ${tile.grainCount}" }

        val oldTileData = getSpecialTileAt(location)
        setSpecialTileAt(tile.location, oldTileData.copy(tile))
    }
}

/**
 * Creates a copy of the tile with the data from the [given tile][from].
 */
fun TileData.copy(from: EditableGameTile): TileData = copy(
    location = from.location,
    tileType = from.type,
    grainCount = from.grainCount,
    hideGrainCount = from.hideGrainCount,
)
