package de.github.dudrie.hamster.de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.de.github.dudrie.hamster.internal.model.territory.EditableGameTile
import de.github.dudrie.hamster.interfaces.AbstractTerritory

abstract class AbstractEditableTerritory : AbstractTerritory() {

    // TODO: Add members
    abstract override fun getTileAt(location: Location): EditableGameTile
}
