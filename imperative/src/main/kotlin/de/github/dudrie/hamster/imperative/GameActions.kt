package de.github.dudrie.hamster.imperative

import de.github.dudrie.hamster.external.model.HamsterSpiel
import de.github.dudrie.hamster.i18n.ErrorString

/**
 * The game used by the imperative commands.
 *
 * If no game is started it is `null`.
 */
internal var imperativeGlobalGame: HamsterSpiel? = null

/**
 * Starts a new hamster game with the given [territoryFile].
 *
 * After loading and starting the game the global [imperativeGlobalGame] is set to this game.
 *
 * There can only be one game started at any point in time.
 *
 * @param territoryFile File to load the territory from. Has to be in the resources/ folder. If not provided a default territory gets loaded.
 */
fun startGame(territoryFile: String? = null) {
    require(imperativeGlobalGame == null) { ErrorString.get("error.ONLY_ONE_GAME_IMPERATIVE_GAME_ALLOWED") }
    val game = HamsterSpiel(territoryFile)
    game.starteSpiel(false)
    imperativeGlobalGame = game
}

/**
 * Stops the current game (if started and present).
 *
 * It does **NOT** change the [imperativeGlobalGame].
 */
fun stopGame() {
    imperativeGlobalGame?.stoppeSpiel()
}

/**
 * Pauses the current game (if started and present).
 */
fun pauseGame() {
    imperativeGlobalGame?.pausiereSpiel()
}

/**
 * Resumes a paused game.
 */
fun resumeGame() {
    imperativeGlobalGame?.setzeSpielFort()
}

/**
 * Sets the [speed] of the game.
 *
 * Must be between [minSpeed][de.github.dudrie.hamster.internal.model.game.GameCommandStack.minSpeed] and [maxSpeed][de.github.dudrie.hamster.internal.model.game.GameCommandStack.maxSpeed].
 *
 * Must be called after calling [startGame] to have an effect.
 */
fun setGameSpeed(speed: Float) {
    imperativeGlobalGame?.gameCommands?.setSpeed(speed)
}
