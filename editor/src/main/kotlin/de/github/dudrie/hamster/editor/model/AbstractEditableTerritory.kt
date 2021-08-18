package de.github.dudrie.hamster.editor.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.interfaces.AbstractTerritory

/**
 * Abstraction for a territory used in the editor.
 */
abstract class AbstractEditableTerritory : AbstractTerritory() {

    /**
     * Returns the tile at the [location].
     */
    abstract override fun getTileAt(location: Location): EditableGameTile

    // TODO: Add members
}
