package de.github.dudrie.hamster.core.exception

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId
import de.github.dudrie.hamster.core.model.util.Position

class KachelBlockiertException(position: Position, kommando: Kommando) :
    SpielException(HamsterString(HamsterStringId.ERR_TILE_BLOCKED), kommando, position)
