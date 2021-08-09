package de.github.dudrie.hamster.internal.model.game

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

class HamsterGame() {
    val territory: GameTerritory

    val paule: GameHamster

    init {
        // TODO: Load settings from file.
        val size = Size(columnCount = 7, rowCount = 5)
        territory = GameTerritory(size = size, tiles = getTilesForTerritory(size))

        paule = GameHamster(
            territory = territory,
            direction = Direction.East,
            location = Location.Origin,
            grainCount = 0
        )
    }

    private fun getTilesForTerritory(size: Size): List<GameTile> {
        val tiles = mutableListOf<GameTile>()
        for (column in 0 until size.columnCount) {
            for (row in 0 until size.rowCount) {
                tiles.add(GameTile(location = Location(column, row), type = GameTileType.Floor, blocked = false))
            }
        }
        return tiles
    }
}
