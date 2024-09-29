package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

class KeineKornerImMundException(hamster: InternerHamster, kommando: Kommando) :
    SpielException(HamsterString(HamsterStringId.ERR_NO_GRAIN_IN_MOUTH), kommando, hamster.position)
