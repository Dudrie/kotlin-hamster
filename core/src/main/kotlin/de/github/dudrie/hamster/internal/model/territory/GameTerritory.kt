package de.github.dudrie.hamster.internal.model.territory

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size

class GameTerritory(val size: Size, val tiles: List<GameTile>) {

    init {
        require(tiles.size == size.columnCount * size.rowCount) { "Count of tiles (${tiles.size}) does not match the size $size." }
        tiles.forEach { it.setTerritory(this) }
    }

    fun getTileAt(location: Location): GameTile {
        val tile = tiles.find { it.location == location }
        require(tile != null) { "There is not tile at location $location" }
        return tile
    }

    fun isTileInside(tile: GameTile): Boolean {
        return this.isLocationInside(tile.location)
    }

    fun isLocationInside(location: Location): Boolean {
        return location.row <= size.rowCount && location.column <= size.columnCount
    }
}
