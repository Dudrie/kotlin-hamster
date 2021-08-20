package de.github.dudrie.hamster.editor.model

import de.github.dudrie.hamster.datatypes.Location

/**
 * Information about a [tile] that gets currently edited by the editor.
 *
 * @param tile Tile that gets edited.
 */
data class EditedTile(val tile: EditableGameTile) {
    /**
     * [Location] of the [tile] that gets edited.
     */
    val location: Location = tile.location
}
