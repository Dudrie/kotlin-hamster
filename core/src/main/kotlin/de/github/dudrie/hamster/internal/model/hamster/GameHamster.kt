package de.github.dudrie.hamster.internal.model.hamster

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileContent

/**
 * Holding all state related to a hamster in the game.
 *
 * @param territory [GameTerritory] this hamster is in.
 * @param tile [GameTile] this hamster is initially on. The tile must be inside the [territory].
 * @param direction The initial [Direction] the hamster faces.
 * @param grainCount The initial amount of grains the hamster has in its mouth. Must be zero or higher.
 */
class GameHamster(
    val territory: GameTerritory,
    tile: GameTile,
    direction: Direction,
    grainCount: Int = 0
) : GameTileContent() {

    init {
        require(territory.isTileInside(tile)) { "The tile of the hamster is outside the territory. Tile's location: ${tile.location}" }
        require(grainCount >= 0) { "The grainCount must be zero or positive. Grain count: $grainCount" }
        tile.addContent(this)
    }

    private val tileState = mutableStateOf(tile)
    private val directionState = mutableStateOf(direction)
    private val grainCountState = mutableStateOf(grainCount)

    /**
     * The [GameTile] the hamster is currently on.
     */
    override val currentTile: GameTile by tileState

    /**
     * The [Direction] the hamster currently faces.
     */
    val direction: Direction by directionState

    /**
     * The amount of grains the hamster currently has in its mouth.
     */
    val grainCount: Int by grainCountState

    override val isBlockingMovement: Boolean = false

    /**
     * Move one step in the [Direction] the hamster is facing.
     *
     * The destination tile must not be blocked.
     *
     * @see moveTo
     */
    fun move() {
        val newLocation = getLocationAfterMove()
        val newTile = territory.getTileAt(newLocation)
        moveTo(newTile)
    }

    /**
     * Moves the hamster to the given [GameTile].
     *
     * The [tile] must not be blocked. After executing this function the hamster is on the new [GameTile] and both tiles, the old and the new one, were updated accordingly.
     *
     * @param tile [GameTile] the hamster should be placed on.
     */
    fun moveTo(tile: GameTile) {
        require(isTileWalkable(tile)) { "The destination tile is blocked or outside the territory. Destination tile's location: ${tile.location}." }
        currentTile.removeContent(this)
        tile.addContent(this)

        tileState.value = tile
    }

    /**
     * Returns the location the hamster would end in if it would make one step.
     *
     * @return The location directly in front of the hamster.
     */
    fun getLocationAfterMove(): Location {
        return currentTile.location.translate(direction.directionVector)
    }

    /**
     * Turns the hamster 90 degrees counterclockwise.
     */
    fun turnLeft() {
        turnTo(direction.left())
    }

    /**
     * Turns the hamster to face in the [newDirection].
     *
     * @param newDirection [Direction] the hamster should face in.
     */
    fun turnTo(newDirection: Direction) {
        directionState.value = newDirection
    }

    /**
     * Increases the internal grain count by one.
     *
     * The tile the hamster stands on must have at least one grain.
     *
     * However, the grain count of the tile is **NOT** updated by this function.
     */
    fun pickGrain() {
        require(currentTile.grainCount > 0) { "The tile beneath the hamster does not have any grains on it." }
        grainCountState.value += 1
    }

    /**
     * Decreases the internal grain count by one.
     *
     * The hamster must have at least one grain in its mouth.
     *
     * The grain count of the tile the hamster stands on is **NOT** updated.
     */
    fun dropGrain() {
        require(grainCount > 0) {
            "Hamster does not have a grain to drop."
        }
        grainCountState.value -= 1
    }

    /**
     * @return Is the given [GameTile] not blocked for movement?
     */
    private fun isTileWalkable(tile: GameTile): Boolean {
        return territory.isTileInside(tile) && !tile.blocked
    }
}
