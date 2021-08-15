package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Superclass for exception that are related to a [GameTile].
 *
 * @param tile [GameTile] related to the exception.
 */
abstract class TileRelatedException(val tile: GameTile) : RuntimeException()
