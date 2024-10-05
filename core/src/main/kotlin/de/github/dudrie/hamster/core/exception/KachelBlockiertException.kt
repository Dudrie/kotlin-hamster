package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId
import de.github.dudrie.hamster.core.model.util.Position

/**
 * [SpielException], die anzeigt, dass die Kachel an der [position] blockiert ist.
 *
 * @param position [Position] der Kachel.
 * @param kommando [Kommando], welches zu dieser [SpielException] geführt hat.
 */
class KachelBlockiertException(position: Position, kommando: Kommando) :
    SpielException(HamsterString(HamsterStringId.ERR_TILE_BLOCKED), kommando, position)
