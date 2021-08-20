package de.github.dudrie.hamster.imperative

import de.github.dudrie.hamster.internal.model.game.GameMode

/**
 * Checks whether the game was properly started in the first place.
 *
 * It throws an error if any of these is true:
 * - The [imperativeGlobalGame] is `null`
 * - The [imperativeGlobalGame] is [stopped][GameMode.Stopped]
 */
internal fun isGameStarted() {
    require(imperativeGlobalGame != null) { "The game has to be started using the startGame() method." }
    require(imperativeGlobalGame?.gameCommands?.mode != GameMode.Stopped) { "The game must not be stopped in order to execute commands." }
}
