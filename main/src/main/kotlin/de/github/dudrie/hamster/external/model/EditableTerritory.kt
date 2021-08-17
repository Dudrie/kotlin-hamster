package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.de.github.dudrie.hamster.internal.model.territory.EditableGameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType

class EditableTerritory(override val territorySize: Size) : AbstractEditableTerritory() {

    private val tiles = mutableListOf<EditableGameTile>()

    init {
        for (location in territorySize.getAllLocationsInside()) {
            tiles.add(EditableGameTile(location, GameTileType.Floor, 0))
        }
    }

    override fun getTileAt(location: Location): EditableGameTile = tiles.first { it.location == location }
}
