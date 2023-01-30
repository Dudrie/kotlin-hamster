package de.github.dudrie.hamster.internal.model.territory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.HamsterLocation

/**
 * Type of [GameTile].
 */
enum class GameTileType {
    /**
     * The [GameTile] is considered a floor on which the hamster can walk.
     */
    Floor,

    /**
     * The [GameTile] is considered a wall which blocks movement.
     */
    Wall
}

/**
 * Holds all the information about a single tile on the game's board.
 *
 * @param location [HamsterLocation] this tile sits on.
 * @param type Type of this tile.
 * @param hideGrainCount Indicates that the tile should hide its exact grain count. It should only show an indicator if it has zero or at least one grain.
 * @param grainCount Initial amount of grains laying on this tile.
 */
open class GameTile(
    val location: HamsterLocation,
    open val type: GameTileType,
    open val hideGrainCount: Boolean,
    grainCount: Int = 0
) {

    private val tileContentState = mutableStateListOf<GameTileContent>()
    protected val grainCountState = mutableStateOf(grainCount)

    /**
     * All [GameTileContent] objects that are on this tile.
     */
    val tileContent = tileContentState.asIterable()

    /**
     * Amount of grains laying on this tile.
     */
    val grainCount by grainCountState

    /**
     * Is this tile blocked for movement?
     */
    val blocked: Boolean
        get() = isBlockingTile() || tileContentState.fold(false) { blocked, element -> blocked || element.isBlockingMovement }

    init {
        require(grainCount >= 0) { "Grain count must be zero or positive." }
    }

    /**
     * Adds all the given [GameTileContent] to this tile.
     *
     * Makes sure that the same [GameTileContent] object is never added more than once.
     *
     * @param contents Contents to add to this tile.
     */
    fun addContent(vararg contents: GameTileContent) {
        for (content in contents) {
            if (!tileContentState.contains(content)) {
                tileContentState += content
            }
        }
    }

    /**
     * Removes all the given [GameTileContent] from this tile.
     *
     * @param contents Contents to remove from this tile.
     */
    fun removeContent(vararg contents: GameTileContent) {
        tileContentState -= contents
    }

    /**
     * Increases the grain count on this tile by one.
     */
    fun addGrainToTile() {
        grainCountState.value++
    }

    /**
     * Decreases the grain count on this tile by one.
     *
     * There has to be at least one grain on this tile for this method to execute successfully.
     */
    fun removeGrainFromTile() {
        require(grainCount > 0) { "There are no grains to pick up at $location." }
        grainCountState.value--
    }

    /**
     * Returns whether this tile can be entered through movement based on its [type].
     *
     * @return Is this tile blocked for movement?
     */
    private fun isBlockingTile(): Boolean = when (type) {
        GameTileType.Floor -> false
        GameTileType.Wall -> true
    }
}
