package de.github.dudrie.hamster.internal.model.game

/**
 * Represents the mode the game is currently in.
 */
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
     * The game is stopped due to an exception during the game.
     */
    Aborted,

    /**
     * The game is stopped due to either all command being executed or a corresponding API got called.
     */
    Stopped
}
