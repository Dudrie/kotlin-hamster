package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId
import de.github.dudrie.hamster.core.model.util.Position

/**
 * [SpielException], die anzeigt, dass die [position] außerhalb des Territoriums liegt.
 *
 * @param position [Position] der Kachel.
 * @param kommando [Kommando], welches zu dieser [SpielException] geführt hat.
 */
class PositionAusserhalbException(position: Position, kommando: Kommando) :
    SpielException(HamsterString(HamsterStringId.ERR_POSITION_NOT_IN_TERRITORY, position), kommando)
