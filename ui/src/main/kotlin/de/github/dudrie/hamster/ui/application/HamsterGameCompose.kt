package de.github.dudrie.hamster.ui.application

import de.github.dudrie.hamster.external.model.HamsterGame
import java.util.concurrent.CountDownLatch

class HamsterGameCompose {
    companion object {
        lateinit var hamsterGame: HamsterGame
    }

    private val initLatch = CountDownLatch(1)
    private val window: GameWindow

    init {
        hamsterGame = HamsterGame("/territories/testTer01.json")
        window = GameWindow(hamsterGame)
    }

    fun startGame() {
        window.show(initLatch)
        initLatch.await()
        hamsterGame.startGame(false)
    }
}
