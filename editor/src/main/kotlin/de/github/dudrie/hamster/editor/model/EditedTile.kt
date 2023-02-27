package de.github.dudrie.hamster.editor.model

import de.github.dudrie.hamster.datatypes.SpielOrt

/**
 * Information about a [tile] that gets currently edited by the editor.
 *
 * @param tile Tile that gets edited.
 */
data class EditedTile(val tile: EditableGameTile) {
    /**
     * [SpielOrt] of the [tile] that gets edited.
     */
    val location: SpielOrt = tile.location
}
