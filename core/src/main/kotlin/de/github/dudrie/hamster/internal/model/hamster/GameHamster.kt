package de.github.dudrie.hamster.internal.model.hamster

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    private val tileState = mutableStateOf(tile)
    private val directionState = mutableStateOf(direction)
    private val grainCountState = mutableStateOf(grainCount)

    override val currentTile: GameTile by tileState

    var direction: Direction by directionState

    var grainCount: Int by grainCountState

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

        tileState.value = tile
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
