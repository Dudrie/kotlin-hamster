package de.github.dudrie.hamster.ui.application

import de.github.dudrie.hamster.interfaces.IHamsterGame
import java.util.concurrent.CountDownLatch

/**
 * Wrapper class to handle the start of a [GameWindow] which shows the UI for the given hamsterGame.
 *
 * @param hamsterGame Game which serves the data to the created window.
 */
class HamsterGameCompose(hamsterGame: IHamsterGame) {

    /**
     * Indicates when the [GameWindow] is ready.
     */
    private val initLatch = CountDownLatch(1)

    /**
     * Window showing the actual game.
     */
    private val window: GameWindow = GameWindow(hamsterGame)

    /**
     * Starts the game.
     *
     * This functions waits until the [GameWindow] is shown.
     */
    fun startGame() {
        window.show(initLatch)
        initLatch.await()
    }
}
