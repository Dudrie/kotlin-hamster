package de.github.dudrie.hamster.presentation

import de.github.dudrie.hamster.ui.game.HamsterGameCompose
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val game = ChangingHamsterGame("/territories/defaultTerritory.json", false)
        val compose = HamsterGameCompose(game)
        compose.startGame()
        compose.window.changeConsoleVisibility()

        while (true) {
            game.changeGame("/territories/defaultTerritory.json")
            delay(500)

            game.paule.laufe()
            game.paule.laufe()

            game.changeGame("/territories/testTer_1.json")
            delay(500)

            game.paule.laufe()
            game.paule.laufe()
            game.paule.dreheNachLinks()
        }
    }
}
