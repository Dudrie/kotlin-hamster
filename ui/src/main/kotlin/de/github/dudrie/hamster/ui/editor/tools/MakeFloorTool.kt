package de.github.dudrie.hamster.ui.editor.tools

import de.github.dudrie.hamster.internal.model.territory.EditableGameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Changes the tile to be [floor][GameTileType.Floor].
 */
class MakeFloorTool : TileTool() {
    override fun applyToTile(tile: EditableGameTile) {
        tile.type = GameTileType.Floor
    }
}
