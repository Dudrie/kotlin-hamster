package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.datatypes.Location

/**
 * Indicates that the given [Location] is outside the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory].
 *
 * @param location [Location] that is outside the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory].
 */
class DestinationOutsideTerritoryException(private val location: Location) : RuntimeException() {
    /**
     * @return String representation of this exception
     */
    override fun toString(): String = ResString.getWithFormat("error.location.outside.territory", location)
}
