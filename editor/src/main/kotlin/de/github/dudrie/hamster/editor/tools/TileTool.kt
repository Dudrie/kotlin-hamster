package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.editor.model.EditableGameTile

/**
 * Abstraction for tools to manipulate the tiles in the editor.
 */
abstract class TileTool {

    /**
     * Apply this tool to the given [tile].
     */
    abstract fun applyToTile(tile: EditableGameTile)

}
