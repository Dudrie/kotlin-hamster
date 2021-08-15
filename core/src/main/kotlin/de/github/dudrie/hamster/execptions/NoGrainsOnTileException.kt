package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Indicates that the [GameTile] has no grains but an action requires it to have grains on it.
 *
 * @param tile [GameTile] related to this exception.
 *
 * @see TileRelatedException
 */
class NoGrainsOnTileException(tile: GameTile) : TileRelatedException(tile) {
    /**
     * @return String representation of this exception
     */
    override fun toString(): String = ResString.getWithFormat("error.no.grains.on.tile", tile.location)
}
