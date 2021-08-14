package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.internal.model.territory.GameTile

class NoGrainsOnTileException(tile: GameTile) : TileRelatedException(tile) {
    override fun toString(): String = ResString.getWithFormat("error.no.grains.on.tile", tile.location)
}
