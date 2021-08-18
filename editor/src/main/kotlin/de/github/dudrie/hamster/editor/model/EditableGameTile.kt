package de.github.dudrie.hamster.editor.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Holds the information about a [GameTile] that gets used in the editor and which configuration can be changed.
 *
 * @param location Location of the tile.
 * @param type Initial [GameTileType] of the tile.
 * @param grainCount Initial number of grains on the tile. Defaults to 0.
 */
class EditableGameTile(
    location: Location,
    type: GameTileType,
    grainCount: Int = 0
) : GameTile(location, type, grainCount) {

    private val typeState = mutableStateOf(type)

    override var type: GameTileType by typeState

    /**
     * Sets the grain count on this tile to the given [count].
     *
     * [count] must be zero or greater.
     */
    fun setGrainCount(count: Int) {
        require(count >= 0) { "The new grain count ($count) is negative. It must be zero or positive." }
        grainCountState.value = count
    }

    /**
     * Does this tile have (at least) one content which is a [HamsterTileContent].
     */
    fun hasHamsterContent(): Boolean {
        for (content in tileContent) {
            if (content is HamsterTileContent) {
                return true
            }
        }

        return false
    }

    /**
     * Can this tile be a wall?
     *
     * A tile can be a wall if it does not have any [grains][grainCount] and it does not have a hamster on it.
     *
     * @see hasHamsterContent
     */
    fun canTileBeAWall(): Boolean = grainCount == 0 && !hasHamsterContent()
}
