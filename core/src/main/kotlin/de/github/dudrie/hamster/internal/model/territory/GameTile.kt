package de.github.dudrie.hamster.internal.model.territory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Location

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
 * @param location [Location] this tile sits on.
 * @param type Type of this tile.
 * @param grainCount Initial amount of grains laying on this tile.
 */
class GameTile(
    val location: Location,
    val type: GameTileType,
    grainCount: Int = 0
) {

    private var _territory: GameTerritory? = null

    private val tileContentState = mutableStateListOf<GameTileContent>()

    /**
     * All [GameTileContent] objects that are on this tile.
     */
    val tileContent = tileContentState.asIterable()

    private val grainCountState = mutableStateOf(grainCount)

    /**
     * Amount of grains laying on this tile.
     */
    val grainCount by grainCountState

    /**
     * Is this tile blocked for movement?
     */
    val blocked: Boolean
        get() = isBlockingTile() || tileContentState.fold(false) { blocked, element -> blocked || element.isBlockingMovement }

    /**
     * The territory this tile sits in.
     *
     * Must be set through [setTerritory] before this property can be accessed.
     *
     * @see setTerritory
     */
    val territory: GameTerritory
        get() {
            require(_territory != null) { "Game territory not initialised. You must call setTerritory() before accessing this property." }
            return _territory!!
        }

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
     * Sets the territory of this tile.
     *
     * The [location] of this tile must be inside the [territory].
     *
     * @param territory Territory this tile should be in.
     */
    fun setTerritory(territory: GameTerritory) {
        require(territory.isLocationInside(location)) { "The tile's location $location is outside the territory. Territory size: ${territory.size}" }
        _territory = territory
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
