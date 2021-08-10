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
        get() = _blocked || tileContent.fold(false) { blocked, element -> blocked || element.isBlockingMovement }

    val territory: GameTerritory
        get() {
            require(_territory != null) { "Game territory not initialised. You must call setTerritory() before accessing this property." }
            return _territory!!
        }

    fun addContent(vararg contents: GameTileContent) {
        for (content in contents) {
            if (!tileContent.contains(content)) {
                tileContent += content
            }
        }
    }

    fun removeContent(vararg contents: GameTileContent) {
        tileContent -= contents
    }

    fun setTerritory(territory: GameTerritory) {
        _territory = territory
    }

}
