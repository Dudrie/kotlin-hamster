package de.github.dudrie.hamster.execptions

import de.github.dudrie.hamster.internal.model.game.Command

/**
 * The [exception] that would be thrown if the [failedCommand] got executed.
 */
data class GameException(val exception: RuntimeException, val failedCommand: Command)
