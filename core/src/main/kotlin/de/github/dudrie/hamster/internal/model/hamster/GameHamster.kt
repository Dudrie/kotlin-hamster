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

    var direction: Direction
        get() = directionState.value
        private set(value) {
            directionState.value = value
        }

    private var grainCount: Int
        get() = grainCountState.value
        set(value) {
            grainCountState.value = value
        }


    override val isBlockingMovement: Boolean = false

    fun move() {
        val newLocation = getLocationAfterMove()
        val newTile = territory.getTileAt(newLocation)
        moveTo(newTile)
    }

    fun moveTo(tile: GameTile) {
        require(isTileWalkable(tile)) { "The destination tile is blocked or outside the territory. Destination tile's location: ${tile.location}." }
        currentTile.removeContent(this)
        tile.addContent(this)

        currentTile = tile
    }

    fun getLocationAfterMove(): Location {
        return currentTile.location.translate(direction.directionVector)
    }

    fun turnLeft() {
        turnTo(direction.left())
    }

    fun turnTo(newDirection: Direction) {
        direction = newDirection
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

    private fun isTileWalkable(tile: GameTile): Boolean {
        return territory.isTileInside(tile) && !tile.blocked
    }
}
