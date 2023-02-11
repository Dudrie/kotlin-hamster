package de.github.dudrie.hamster.external.model

/**
 * Abstraction of a simple hamster game.
 */
abstract class SimpleHamsterGame(territoryFile: String? = null) {

    /**
     * Contains the default hamster which is named paule.
     */
    protected val paule: Hamster

    /**
     * Actual game.
     */
    private val game: HamsterGame = HamsterGame(territoryFile)

    init {
        game.startGame()

        paule = game.paule
    }

    /**
     * Method to implement the game designed to be overridden by subclasses.
     *
     * Put the hamster code into this method.
     */
    protected abstract fun run()

    /**
     * Runs the game implemented in the [run] method.
     */
    public fun doRun() {
        try {
            run()
        } catch (e: RuntimeException) {
            throw e
        }

        game.stopGame()
    }

    /**
     * Sets the [speed] of the game.
     *
     * Must be between [minSpeed][de.github.dudrie.hamster.internal.model.game.GameCommandStack.minSpeed] and [maxSpeed][de.github.dudrie.hamster.internal.model.game.GameCommandStack.maxSpeed].
     */
    protected fun setGameSpeed(speed: Float) {
        game.gameCommands.setSpeed(speed)
    }
}
