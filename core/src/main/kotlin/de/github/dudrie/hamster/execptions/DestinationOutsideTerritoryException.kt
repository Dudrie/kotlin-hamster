package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.datatypes.Location

class DestinationOutsideTerritoryException(private val location: Location) : RuntimeException() {
    override fun toString(): String = ResString.getWithFormat("error.location.outside.territory", location)
}
