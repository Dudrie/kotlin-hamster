package de.github.dudrie.hamster.ui.game

import de.github.dudrie.hamster.interfaces.AbstraktesHamsterSpiel
import de.github.dudrie.hamster.ui.application.windows.GameWindow
import java.util.concurrent.CountDownLatch

/**
 * Wrapper class to handle the start of a [GameWindow] which shows the UI for the given hamsterGame.
 *
 * @param hamsterGame Game which serves the data to the created window.
 */
class HamsterGameCompose(hamsterGame: AbstraktesHamsterSpiel) {

    /**
     * Indicates when the [GameWindow] is ready.
     */
    private val initLatch = CountDownLatch(1)

    /**
     * Window showing the actual game.
     */
    private val window: GameWindow = GameWindow(hamsterGame, initLatch)

    init {
        window.show()
    }

    /**
     * Starts the game.
     *
     * This functions waits until the [GameWindow] is shown if it is not already visible.
     */
    fun startGame() {
        initLatch.await()
    }
}
