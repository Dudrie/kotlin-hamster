package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Indicates that the [tile] is blocked and it cannot hold a [GameHamster][de.github.dudrie.hamster.internal.model.hamster.GameHamster].
 *
 * @param tile Related tile.
 *
 * @see TileRelatedException
 */
class TileBlockedException(tile: GameTile) : TileRelatedException(tile) {
    override fun toString(): String = HamsterString.getWithFormat("error.tile.blocked", tile.location)
}
