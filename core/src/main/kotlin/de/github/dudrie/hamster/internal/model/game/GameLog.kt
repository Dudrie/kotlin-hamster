package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.mutableStateListOf

/**
 * Holds all messages which should appear as game log to the user.
 */
class GameLog {
    private val messagesState = mutableStateListOf<String>()

    /**
     * All messages saved in the [GameLog].
     */
    val messages = messagesState.asIterable()

    /**
     * Amount of messages saved in the log.
     */
    val messageCount: Int
        get() = messagesState.size

    /**
     * Adds a [message] to the [GameLog].
     *
     * Also, the message gets printed to the default console as well.
     */
    fun addMessage(message: String) {
        messagesState.add(message)
        println("[GameLog] $message")
    }

    /**
     * Removes the last message from the [GameLog].
     */
    fun removeLastMessage() {
        messagesState.removeLast()
    }
}
