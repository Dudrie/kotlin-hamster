package de.github.dudrie.hamster.editor.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Holds the data and helpers necessary to edit a territory.
 *
 * @param territorySize Size of the territory.
 */
class EditableTerritory(override val territorySize: Size) : AbstractEditableTerritory() {

    /**
     * Tiles currently present in the territory.
     */
    private val tiles = mutableListOf<EditableGameTile>()

    init {
        for (location in territorySize.getAllLocationsInside()) {
            tiles.add(EditableGameTile(location, GameTileType.Floor, 0))
        }
    }

    /**
     * Returns the tile at the given [location].
     *
     * @throws NoSuchElementException If there is no tile at the [location].
     */
    override fun getTileAt(location: Location): EditableGameTile = tiles.first { it.location == location }
}
