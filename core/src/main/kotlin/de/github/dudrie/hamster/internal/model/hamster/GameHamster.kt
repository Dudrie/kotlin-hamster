package de.github.dudrie.hamster.internal.model.hamster

import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileContent
import de.github.dudrie.hamster.internal.model.territory.GameTileContentType

class GameHamster(
    val territory: GameTerritory,
    location: Location,
    direction: Direction,
    grainCount: Int = 0
) : GameTileContent(GameTileContentType.Hamster) {

    init {
        require(territory.isLocationInside(location)) { "The location of the hamster is outside the territory. Location: $location" }
        require(grainCount >= 0) { "The grainCount must be zero or positive. Grain count: $grainCount" }
    }

    val locationState = mutableStateOf(location)
    val directionState = mutableStateOf(direction)
    val grainCountState = mutableStateOf(grainCount)

    private var location: Location
        get() = locationState.value
        set(value) {
            locationState.value = value
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

    override val currentTile: GameTile
        get() = territory.getTileAt(location)

    override val isBlocking: Boolean = false

    fun move() {
        location = when (direction) {
            Direction.North -> location.copy(row = location.row - 1)
            Direction.East -> location.copy(column = location.column + 1)
            Direction.South -> location.copy(row = location.row + 1)
            Direction.West -> location.copy(column = location.column - 1)
        }
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
