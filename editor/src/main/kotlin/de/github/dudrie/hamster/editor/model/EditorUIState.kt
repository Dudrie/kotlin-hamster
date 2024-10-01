package de.github.dudrie.hamster.editor.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.tools.SelectTileTool
import de.github.dudrie.hamster.editor.tools.TileTool

class EditorUIState : ViewModel() {

    var tiles by mutableStateOf(mapOf<Position, Kachel>())
        private set

    var selectedTool by mutableStateOf<TileTool>(SelectTileTool)

    fun getTileAt(position: Position): Kachel =
        tiles[position] ?: throw NoSuchElementException("ERR_NO_TILE_AT_POSITION")

    fun replaceTile(position: Position, newTile: Kachel) {
        val newTiles = tiles.toMutableMap()
        newTiles[position] = newTile
        tiles = newTiles
    }

}
