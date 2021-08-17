package de.github.dudrie.hamster.internal.model.hamster

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileContent

/**
 * Abstraction representing a hamster on a [GameTile].
 *
 * @param tile Initial tile the hamster is on.
 * @param direction Initial direction the hamster faces in.
 * @param grainCount Initial number of grains in the hamster's mouth.
 */
sealed class HamsterTileContent(tile: GameTile, direction: Direction, grainCount: Int = 0) : GameTileContent() {
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
     * Sets the tile the hamster is on to the given [newTile].
     *
     * Both tiles, the old and the new ones, have their contents updated accordingly.
     */
    protected fun setTile(newTile: GameTile) {
        currentTile.removeContent(this)
        newTile.addContent(this)

        tileState.value = newTile
    }

    /**
     * Sets the [direction] of the hamster.
     */
    protected fun setDirection(direction: Direction) {
        directionState.value = direction
    }

    /**
     * Sets the grain count to the given [newCount].
     *
     * The [newCount] must be zero or positive.
     */
    protected fun setGrainCount(newCount: Int) {
        require(newCount >= 0) { "The new grain count ($newCount) must be zero or greater." }
        grainCountState.value = newCount
    }
}
