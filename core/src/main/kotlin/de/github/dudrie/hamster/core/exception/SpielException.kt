package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString

sealed class SpielException(val nachricht: HamsterString, val kommando: Kommando) :
    Exception()
