package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.internal.model.territory.GameTile

class PathBlockedException(destinationTile: GameTile) : TileRelatedException(destinationTile) {
    override fun toString(): String = ResString.getWithFormat("error.path.blocked", tile.location)
}
