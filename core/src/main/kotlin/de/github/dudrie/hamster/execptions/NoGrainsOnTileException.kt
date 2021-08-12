package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

class NoGrainsOnTileException(private val tile: GameTile) : RuntimeException() {
    override fun toString(): String = "There are no grains to pick up on the tile at ${tile.location}."
}
