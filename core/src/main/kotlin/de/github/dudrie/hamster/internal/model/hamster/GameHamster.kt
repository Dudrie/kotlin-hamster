package de.github.dudrie.hamster.internal.model.hamster

import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileContent
import de.github.dudrie.hamster.internal.model.territory.GameTileContentType

class GameHamster(
    val territory: GameTerritory,
    tile: GameTile,
    direction: Direction,
    grainCount: Int = 0
) : GameTileContent(GameTileContentType.Hamster) {

    init {
        require(territory.isTileInside(tile)) { "The tile of the hamster is outside the territory. Tile's location: ${tile.location}" }
        require(grainCount >= 0) { "The grainCount must be zero or positive. Grain count: $grainCount" }
        tile.addContent(this)
    }

    val tileState = mutableStateOf(tile)
    val directionState = mutableStateOf(direction)
    val grainCountState = mutableStateOf(grainCount)

    override var currentTile: GameTile
        get() = tileState.value
        set(value) {
            tileState.value = value
        }

    private var direction: Direction
        get() = directionState.value
        set(value) {
            directionState.value = value
        }

    private var grainCount: Int
        get() = grainCountState.value
        set(value) {
            grainCountState.value = value
        }


    override val isBlockingMovement: Boolean = false

    fun move() {
        val location = currentTile.location
        val newLocation = when (direction) {
            Direction.North -> location.copy(row = location.row - 1)
            Direction.East -> location.copy(column = location.column + 1)
            Direction.South -> location.copy(row = location.row + 1)
            Direction.West -> location.copy(column = location.column - 1)
        }
        val newTile = territory.getTileAt(newLocation)

        require(territory.isTileWalkable(newTile)) { "The destination tile is blocked or outside the territory. Destination tile's location: ${newTile.location}." }
        currentTile.removeContent(this)
        newTile.addContent(this)

        currentTile = newTile
    }

    fun turnLeft() {
        direction = when (direction) {
            Direction.North -> Direction.West
            Direction.East -> Direction.North
            Direction.South -> Direction.East
            Direction.West -> Direction.South
        }
    }

    fun pickGrain() {
        grainCount += 1
    }

    fun dropGrain() {
        require(grainCount > 0) {
            "Hamster does not have a grain to drop."
        }
        grainCount -= 1
    }
}
