package de.github.dudrie.hamster.internal.model.game

enum class GameMode {
    /**
     * The game gets initialised (ie loading the territory and placing the default hamster).
     */
    Initializing,

    /**
     * The game is running and executing commands.
     */
    Running,

    /**
     * The game is paused and can be resumed. There are still commands which will be executed after the game is resumed.
     */
    Paused,

    /**
     * The game is stopped due because all commands have been executed.
     */
    Stopped,

    /**
     * The game was aborted through a user action or a similar function invocation.
     *
     * Unlike Stopped the execution of commands has not finished but was interrupted.
     */
    Aborted;

    fun isStoppedOrAborted(): Boolean {
        return this == Stopped || this == Aborted
    }
}
