package de.github.dudrie.hamster.imperative

import de.github.dudrie.hamster.external.model.HamsterGame

internal var imperativeGlobalGame: HamsterGame? = null
fun startGame(territoryFile: String? = null) {
    val game = HamsterGame(territoryFile)
    game.startGame(true)
    imperativeGlobalGame = game
}

fun stopGame() {
    imperativeGlobalGame?.stopGame()
    imperativeGlobalGame = null
}
