package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.Position

/**
 * [Exception], die bei der Ausführung eines [kommando]s aufgetreten ist.
 *
 * @param nachricht [HamsterString], welcher diese [SpielException] beschreibt.
 * @param kommando [Kommando], welches zu dieser [SpielException] geführt hat.
 * @param position _(optional)_ [Position] der Kachel, die zu dieser [SpielException] gehört. Wenn es keine solche [Position] gibt, ist die [position] `null`.
 */
sealed class SpielException(
    val nachricht: HamsterString,
    val kommando: Kommando,
    val position: Position? = null
) :
    Exception()
