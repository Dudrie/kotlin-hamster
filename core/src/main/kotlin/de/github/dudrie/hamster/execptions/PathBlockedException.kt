package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

class PathBlockedException(private val destinationTile: GameTile) : RuntimeException() {
    override fun toString(): String {
        return "Tile at ${destinationTile.location} is blocked."
    }
}
