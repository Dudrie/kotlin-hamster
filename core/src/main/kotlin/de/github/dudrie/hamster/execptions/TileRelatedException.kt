package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

abstract class TileRelatedException(val tile: GameTile) : RuntimeException() {
}
