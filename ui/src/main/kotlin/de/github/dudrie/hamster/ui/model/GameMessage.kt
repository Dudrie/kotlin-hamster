package de.github.dudrie.hamster.ui.model

/**
 * Message to show in the game's console.
 *
 * @param text The text of the message.
 * @param type The [GameMessageType] of the message.
 *
 * @see GameMessageType
 */
data class GameMessage(val text: String, val type: GameMessageType)

/**
 * Type of the [GameMessage].
 */
enum class GameMessageType {
    /**
     * Message related to an executed command.
     */
    COMMAND,

    /**
     * Command related to an error.
     */
    ERROR,

    /**
     * Command which is an information to the user.
     */
    INFO
}
