package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

class DestinationOutsideTerritoryException(destinationTile: GameTile) : TileRelatedException(destinationTile) {
    override fun toString(): String = "Tile at ${tile.location} is outside the territory."
}
