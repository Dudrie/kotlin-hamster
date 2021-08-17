package de.github.dudrie.hamster.internal.model.territory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent

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
}
