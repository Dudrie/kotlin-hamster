package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.model.util.HamsterString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.max
import kotlin.math.min

class SpielLog {
    private val _nachrichten = MutableStateFlow(listOf<HamsterString>())
    val nachrichten = _nachrichten.asStateFlow()

    private val _zeigeBisIndex = MutableStateFlow(0)
    val zeigeBisIndex = _zeigeBisIndex.asStateFlow()

    fun fugeNachrichtHinzu(nachricht: HamsterString) {
        _nachrichten.update {
            val liste = it.toMutableList()
            liste.add(0, nachricht)
            liste
        }
        _zeigeBisIndex.update { it + 1 }
    }

    fun zeigeEineNachrichtWeniger() {
        setZeigeBis(max(0, zeigeBisIndex.value - 1))
    }

    fun zeigeEineNachrichtMehr() {
        setZeigeBis(min(zeigeBisIndex.value + 1, nachrichten.value.size))
    }

    private fun setZeigeBis(index: Int) {
        require(index in 0..nachrichten.value.size + 1) { "ERR_OUTSIDE_OF_MESSAGE_RANGE" }
        _zeigeBisIndex.update { index }
    }

    fun zeigeAlleNachrichten() {
        setZeigeBis(nachrichten.value.size)
    }

}
