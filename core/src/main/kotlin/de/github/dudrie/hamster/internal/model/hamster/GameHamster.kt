package de.github.dudrie.hamster.internal.model.hamster

import de.github.dudrie.hamster.datatypes.Richtung
import de.github.dudrie.hamster.datatypes.HamsterOrt
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Holds all state related to a hamster in the game.
 *
 * @param territory [GameTerritory] this hamster is in.
 * @param tile [GameTile] this hamster is initially on. The tile must be inside the [territory].
 * @param direction The initial [Richtung] the hamster faces.
 * @param grainCount The initial amount of grains the hamster has in its mouth. Must be zero or higher.
 */
class GameHamster(
    val territory: GameTerritory,
    tile: GameTile,
    direction: Richtung,
    grainCount: Int = 0
) : HamsterTileContent(tile, direction, grainCount) {

    /**
     * Number of moves this hamster has taken.
     */
    var movesTaken: Int = 0
        private set

    /**
     * Number of the hamster.
     */
    val hamsterNumber = territory.getNextHamsterNumber()

    init {
        require(territory.isTileInside(tile)) { "The tile of the hamster is outside the territory. Tile's location: ${tile.location}" }
        require(grainCount >= 0) { "The grainCount must be zero or positive. Grain count: $grainCount" }
    }

    /**
     * Move one step in the [Richtung] the hamster is facing.
     *
     * The destination tile must not be blocked. After a successful move [movesTaken] is increased by 1.
     *
     * @see moveTo
     */
    fun move() {
        val newLocation = getLocationAfterMove()
        val newTile = territory.getTileAt(newLocation)
        moveTo(newTile)
        movesTaken++
    }

    /**
     * Moves the hamster to the given [GameTile].
     *
     * The [tile] must not be blocked. After executing this function the hamster is on the new [GameTile] and both tiles, the old and the new one, were updated accordingly. The [movesTaken] variable **NOT** changed within this function!
     *
     * @param tile [GameTile] the hamster should be placed on.
     */
    fun moveTo(tile: GameTile) {
        require(isTileWalkable(tile)) { "The destination tile is blocked or outside the territory. Destination tile's location: ${tile.location}." }

        setTile(tile)
    }

    /**
     * Decreases the [movesTaken] of this hamster by on.
     */
    internal fun decreaseMovesTakenByOne() {
        require(movesTaken > 0) { "The move count must be greater than zero to be decreased." }
        movesTaken--
    }

    /**
     * Returns the location the hamster would end in if it would make one step.
     *
     * @return The location directly in front of the hamster.
     */
    fun getLocationAfterMove(): HamsterOrt {
        return tile.location.translate(direction.directionVector)
    }

    /**
     * Turns the hamster 90 degrees counterclockwise.
     */
    fun turnLeft() {
        turnTo(direction.nachLinksGedreht())
    }

    /**
     * Turns the hamster to face in the [newDirection].
     *
     * @param newDirection [Richtung] the hamster should face in.
     */
    fun turnTo(newDirection: Richtung) {
        setDirection(newDirection)
    }

    /**
     * Increases the internal grain count by one.
     *
     * The tile the hamster stands on must have at least one grain.
     *
     * However, the grain count of the tile is **NOT** updated by this function.
     */
    fun pickGrain() {
        setGrainCount(grainCount + 1)
    }

    /**
     * Decreases the internal grain count by one.
     *
     * The hamster must have at least one grain in its mouth.
     *
     * The grain count of the tile the hamster stands on is **NOT** updated.
     */
    fun dropGrain() {
        require(grainCount > 0) { "Hamster does not have a grain to drop." }
        setGrainCount(grainCount - 1)
    }

    /**
     * @return Is the given [GameTile] not blocked for movement?
     */
    private fun isTileWalkable(tile: GameTile): Boolean {
        return territory.isTileInside(tile) && !tile.blocked
    }
}
