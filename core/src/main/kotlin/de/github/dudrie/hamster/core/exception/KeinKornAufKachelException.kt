package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId
import de.github.dudrie.hamster.core.model.util.Position

/**
 * [SpielException], die anzeigt, dass auf der Kachel an der [position] keine Körner liegen.
 *
 * @param position [Position] der Kachel.
 * @param kommando [Kommando], welches zu dieser [SpielException] geführt hat.
 */
class KeinKornAufKachelException(position: Position, kommando: Kommando) :
    SpielException(
        HamsterString(HamsterStringId.ERR_NO_GRAIN_ON_TILE, position),
        kommando,
        position
    )
