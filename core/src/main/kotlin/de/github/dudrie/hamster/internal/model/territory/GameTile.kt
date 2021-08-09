package de.github.dudrie.hamster.internal.model.territory

import androidx.compose.runtime.mutableStateListOf
import de.github.dudrie.hamster.datatypes.Location

enum class GameTileType {
    Floor, Wall
}

class GameTile(
    val location: Location,
    val type: GameTileType,
    blocked: Boolean = false
) {

    private var _blocked: Boolean = blocked
    private var _territory: GameTerritory? = null

    val tileContent = mutableStateListOf<GameTileContent>()

    val blocked: Boolean
        get() = _blocked || tileContent.fold(false) { blocked, element -> blocked || element.isBlocking }

    val territory: GameTerritory
        get() {
            require(_territory != null) { "Game territory not initialised" }
            return _territory!!
        }

    fun addContent(vararg content: GameTileContent) {
        tileContent += content
    }

    fun setTerritory(territory: GameTerritory) {
        _territory = territory
    }

}
