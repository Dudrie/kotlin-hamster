package de.github.dudrie.kotlin.hamster.ui.application

import java.util.concurrent.CountDownLatch

class HamsterGameUI {
    private val initLatch = CountDownLatch(1)
    private val window: GameWindow = GameWindow()

    fun startGame() {
        window.show(initLatch)
        initLatch.await()
        println("GAME STARTED")
    }
}
