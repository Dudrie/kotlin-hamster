package de.github.dudrie.hamster.imperative

import de.github.dudrie.hamster.i18n.ErrorString
import de.github.dudrie.hamster.internal.model.game.GameMode

/**
 * Checks whether the game was properly started in the first place.
 *
 * It throws an error if any of these is true:
 * - The [imperativeGlobalGame] is `null`
 * - The [imperativeGlobalGame] is [stopped][GameMode.Aborted]
 */
internal fun isGameStarted() {
    require(imperativeGlobalGame != null) { ErrorString.get("error.execute.command.GAME_HAS_TO_BE_STARTED") }
    require(imperativeGlobalGame?.gameCommands?.mode != GameMode.Aborted) { ErrorString.get("error.execute.command.GAME_IS_ABORTED") }
    require(imperativeGlobalGame?.gameCommands?.mode != GameMode.Stopped) { ErrorString.get("error.execute.command.GAME_IS_STOPPED") }
}
