package de.github.dudrie.hamster.ui.interfaces

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.internal.model.territory.GameTile

interface ITerritory {
    /**
     * [Size] of this territory.
     */
    val territorySize: Size

    /**
     * Returns the [GameTile] at the [location].
     *
     * The [location] must be inside this territory.
     */
    fun getTileAt(location: Location): GameTile
}
