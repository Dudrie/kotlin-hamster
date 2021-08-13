package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.mutableStateListOf

class GameLog {
    private val messagesState = mutableStateListOf<String>()
    val messages = messagesState.asIterable()

    fun addMessage(message: String) {
        messagesState.add(message)
        println("[GameLog] $message")
    }

    fun removeLastMessage() {
        messagesState.removeLast()
    }
}
