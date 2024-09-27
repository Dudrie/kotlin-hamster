package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.Position

class KachelBlockiertException(position: Position, kommando: Kommando) :
    SpielException(HamsterString("ERR_TILE_BLOCKED", position), kommando)
