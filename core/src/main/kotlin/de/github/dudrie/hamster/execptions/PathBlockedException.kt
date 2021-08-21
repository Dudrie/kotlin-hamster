package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Indicates that the path of the [GameHamster][de.github.dudrie.hamster.internal.model.hamster.GameHamster] is blocked and the [GameHamster][de.github.dudrie.hamster.internal.model.hamster.GameHamster] tried to move.
 *
 * @param destinationTile Blocked tile the [GameHamster][de.github.dudrie.hamster.internal.model.hamster.GameHamster] was about to move on.
 *
 * @see TileRelatedException
 */
class PathBlockedException(destinationTile: GameTile) : TileRelatedException(destinationTile) {
    /**
     * @return String representation of this exception
     */
    override fun toString(): String = HamsterString.getWithFormat("error.path.blocked", tile.location)
}
