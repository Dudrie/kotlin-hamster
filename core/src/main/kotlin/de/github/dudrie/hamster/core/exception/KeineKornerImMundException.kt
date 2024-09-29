package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.util.HamsterString

class KeineKornerImMundException(hamster: InternerHamster, kommando: Kommando) :
    SpielException(HamsterString("ERR_NO_GRAIN_IN_MOUTH"), kommando)
