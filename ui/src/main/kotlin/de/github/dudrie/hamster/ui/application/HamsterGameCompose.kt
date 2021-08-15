package de.github.dudrie.hamster.ui.application

import de.github.dudrie.hamster.interfaces.IHamsterGame
import java.util.concurrent.CountDownLatch

class HamsterGameCompose(private val hamsterGame: IHamsterGame) {

    private val initLatch = CountDownLatch(1)
    private val window: GameWindow = GameWindow(hamsterGame)

    fun startGame(startPaused: Boolean = true) {
        window.show(initLatch)
        initLatch.await()
        hamsterGame.startGame(startPaused)
    }
}
