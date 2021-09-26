package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.editor.model.EditableGameTile

/**
 * Adds one grain to the tile.
 */
class AddGrainToTileTool : TileTool() {
    override fun applyToTile(tile: EditableGameTile) {
        tile.addGrainToTile()
    }
}
