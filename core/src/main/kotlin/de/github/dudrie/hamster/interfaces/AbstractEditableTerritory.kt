package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.territory.EditableGameTile

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
