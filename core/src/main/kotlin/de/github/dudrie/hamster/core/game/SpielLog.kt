package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.model.util.HamsterString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpielLog {
    private val _nachrichten = MutableStateFlow(listOf<HamsterString>())
    val nachrichten = _nachrichten.asStateFlow()

    fun zeigeNachricht(nachricht: HamsterString) {
        _nachrichten.update { it + nachricht }
    }

    fun zeigeMehrereNachrichten(nachrichten: List<HamsterString>) {
        _nachrichten.update { it + nachrichten }
    }

    fun entferneLetzteNachricht() {
        _nachrichten.update {
            val neueListe = it.toMutableList()
            neueListe.removeLast()
            neueListe
        }
    }
}
