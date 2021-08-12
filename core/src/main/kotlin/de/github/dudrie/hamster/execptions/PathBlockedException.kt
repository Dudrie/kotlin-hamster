package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

class PathBlockedException(private val destinationTile: GameTile) : RuntimeException() {
    override fun toString(): String = "Tile at ${destinationTile.location} is blocked for movement."
}
