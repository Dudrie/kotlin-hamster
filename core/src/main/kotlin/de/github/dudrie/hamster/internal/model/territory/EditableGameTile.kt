package de.github.dudrie.hamster.de.github.dudrie.hamster.internal.model.territory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

class EditableGameTile(
    location: Location,
    type: GameTileType,
    grainCount: Int = 0
) : GameTile(location, type, grainCount) {

    private val typeState = mutableStateOf(type)

    override var type: GameTileType by typeState

    fun setGrainCount(count: Int) {
        require(count >= 0) { "The new grain count ($count) is negative. It must be zero or positive." }
        grainCountState.value = count
    }
}
