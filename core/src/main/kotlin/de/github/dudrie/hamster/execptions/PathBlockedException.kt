package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

class PathBlockedException(destinationTile: GameTile) : TileRelatedException(destinationTile) {
    override fun toString(): String = "Tile at ${tile.location} is blocked for movement."
}
