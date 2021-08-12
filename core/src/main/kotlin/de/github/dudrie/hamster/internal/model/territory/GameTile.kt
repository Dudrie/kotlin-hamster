package de.github.dudrie.hamster.internal.model.territory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.datatypes.Location

enum class GameTileType {
    Floor, Wall
}

class GameTile(
    val location: Location,
    val type: GameTileType,
) {

    private var _territory: GameTerritory? = null

    private val tileContentState = mutableStateListOf<GameTileContent>()
    val tileContent = tileContentState.asIterable()

    private val grainCountState = mutableStateOf(0)
    val grainCount by grainCountState

    val blocked: Boolean
        get() = isBlockingTile() || tileContentState.fold(false) { blocked, element -> blocked || element.isBlockingMovement }

    val territory: GameTerritory
        get() {
            require(_territory != null) { "Game territory not initialised. You must call setTerritory() before accessing this property." }
            return _territory!!
        }

    fun addContent(vararg contents: GameTileContent) {
        for (content in contents) {
            if (!tileContentState.contains(content)) {
                tileContentState += content
            }
        }
    }

    fun removeContent(vararg contents: GameTileContent) {
        tileContentState -= contents
    }

    fun setTerritory(territory: GameTerritory) {
        _territory = territory
    }

    private fun isBlockingTile(): Boolean = when (type) {
        GameTileType.Floor -> false
        GameTileType.Wall -> true
    }
}
