package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

/**
 * [SpielException], die anzeigt, dass der Hamster keine Körner im Mund hat.
 *
 * @param hamster Der [InternerHamster], der keine Körner im Mund hat.
 * @param kommando [Kommando], welches zu dieser [SpielException] geführt hat.
 */
class KeineKornerImMundException(hamster: InternerHamster, kommando: Kommando) :
    SpielException(HamsterString(HamsterStringId.ERR_NO_GRAIN_IN_MOUTH), kommando, hamster.position)
