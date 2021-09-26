package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.editor.model.EditableGameTile

/**
 * Removes on grain from the tile if the tile has at least one grain on it.
 */
class RemoveGrainFromTileTool : TileTool() {
    override fun applyToTile(tile: EditableGameTile) {
        if (tile.grainCount > 0) {
            tile.removeGrainFromTile()
        }
    }
}
