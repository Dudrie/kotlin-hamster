package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.Position

class KeinKornAufKachelException(position: Position, kommando: Kommando) :
    SpielException(HamsterString("ERR_NO_GRAIN_ON_TILE", position), kommando)
