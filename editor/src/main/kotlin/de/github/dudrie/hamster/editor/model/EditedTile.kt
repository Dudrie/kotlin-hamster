package de.github.dudrie.hamster.editor.model

import de.github.dudrie.hamster.datatypes.HamsterLocation

/**
 * Information about a [tile] that gets currently edited by the editor.
 *
 * @param tile Tile that gets edited.
 */
data class EditedTile(val tile: EditableGameTile) {
    /**
     * [HamsterLocation] of the [tile] that gets edited.
     */
    val location: HamsterLocation = tile.location
}
