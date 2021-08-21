package de.github.dudrie.hamster.imperative

import de.github.dudrie.hamster.external.model.HamsterGame

/**
 * The game used by the imperative commands.
 *
 * If no game is started it is `null`.
 */
internal var imperativeGlobalGame: HamsterGame? = null

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
    require(imperativeGlobalGame == null) { "There can only be one game used at a time." }
    val game = HamsterGame(territoryFile)
    game.startGame(true)
    imperativeGlobalGame = game
}

/**
 * Stops the current game (if started and present).
 *
 * It does **NOT** change the [imperativeGlobalGame].
 */
fun stopGame() {
    imperativeGlobalGame?.stopGame()
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
