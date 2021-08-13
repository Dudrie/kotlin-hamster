package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

class NoGrainsOnTileException(tile: GameTile) : TileRelatedException(tile) {
    override fun toString(): String = "There are no grains to pick up on the tile at ${tile.location}."
}
