package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.datatypes.HamsterOrt
import de.github.dudrie.hamster.i18n.HamsterString

/**
 * Indicates that the given [HamsterOrt] is outside the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory].
 *
 * @param location [HamsterOrt] that is outside the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory].
 */
class DestinationOutsideTerritoryException(private val location: HamsterOrt) : RuntimeException() {
    /**
     * @return String representation of this exception
     */
    override fun toString(): String = HamsterString.getWithFormat("error.location.outside.territory", location)
}
