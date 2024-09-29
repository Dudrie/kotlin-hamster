package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.model.util.HamsterString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpielLog {
    private val _nachrichten = MutableStateFlow(listOf<HamsterString>())
    val nachrichten = _nachrichten.asStateFlow()

    fun zeigeNachricht(nachricht: HamsterString) {
        _nachrichten.update {
            val liste = it.toMutableList()
            liste.add(0, nachricht)
            liste
        }
    }

    fun zeigeMehrereNachrichten(nachrichten: List<HamsterString>) {
        _nachrichten.update {
            val liste = it.toMutableList()
            liste.addAll(0, nachrichten.asReversed())
            liste
        }
    }

    fun entferneLetzteNachricht() {
        _nachrichten.update {
            val neueListe = it.toMutableList()
            neueListe.removeFirst()
            neueListe
        }
    }
}
