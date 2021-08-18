package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.editor.model.EditableGameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Changes the tile to be a [wall][GameTileType.Wall] if possible.
 */
class MakeWallTool : TileTool() {
    override fun applyToTile(tile: EditableGameTile) {
        if (tile.canTileBeAWall()) {
            tile.type = GameTileType.Wall
        }
    }
}
