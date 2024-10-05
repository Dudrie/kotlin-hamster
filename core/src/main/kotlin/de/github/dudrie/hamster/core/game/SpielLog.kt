package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.model.util.HamsterString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.max
import kotlin.math.min

/**
 * Verwaltet das Log aller Nachrichten in der Spielkonsole.
 */
class SpielLog {
    /**
     * Backing field von [nachrichten].
     */
    private val _nachrichten = MutableStateFlow(listOf<HamsterString>())

    /**
     * Alle Nachrichten die bisher in die Spielkonsole geschickt wurden.
     */
    val nachrichten = _nachrichten.asStateFlow()

    /**
     * Backing field von [zeigeBisIndex].
     */
    private val _zeigeBisIndex = MutableStateFlow(0)

    /**
     * Gibt an, bis zu welchem Index (ausschließlich) die Nachrichten angezeigt werden sollen.
     */
    val zeigeBisIndex = _zeigeBisIndex.asStateFlow()

    /**
     * Fügt die [nachricht] zum Log hinzu und passt [zeigeBisIndex] entsprechend an.
     */
    fun fugeNachrichtHinzu(nachricht: HamsterString) {
        _nachrichten.update {
            val liste = it.toMutableList()
            liste.add(0, nachricht)
            liste
        }
        _zeigeBisIndex.update { it + 1 }
    }

    /**
     * Verringert [zeigeBisIndex] um 1, sofern er noch nicht 0 ist.
     */
    fun zeigeEineNachrichtWeniger() {
        setZeigeBis(max(0, zeigeBisIndex.value - 1))
    }

    /**
     * Erhöht [zeigeBisIndex] um 1, sofern er noch nicht so hoch ist, dass er alle [nachrichten] anzeigt.
     */
    fun zeigeEineNachrichtMehr() {
        setZeigeBis(min(zeigeBisIndex.value + 1, nachrichten.value.size))
    }

    /**
     * Verändert [zeigeBisIndex] so, dass alle Nachrichten bis zu [index] (ausschließlich) angezeigt werden.
     */
    private fun setZeigeBis(index: Int) {
        require(index in 0..nachrichten.value.size + 1) { "ERR_OUTSIDE_OF_MESSAGE_RANGE" }
        _zeigeBisIndex.update { index }
    }

    /**
     * Verändert [zeigeBisIndex] so, dass alle [nachrichten] angezeigt werden.
     */
    fun zeigeAlleNachrichten() {
        setZeigeBis(nachrichten.value.size)
    }

}
