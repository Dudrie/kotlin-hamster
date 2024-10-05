package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium

/**
 * Ergebnis nach der Ausführung eines [Kommando].
 *
 * @param kommando Ausgeführtes [Kommando].
 * @param territoriumVorher Der Zustand des Territoriums vor der Ausführung des [kommando].
 * @param territoriumNachher Der Zustand des Territoriums nach der Ausführung des [kommando].
 */
data class KommandoErgebnis(
    val kommando: Kommando,
    val territoriumVorher: InternesTerritorium,
    val territoriumNachher: InternesTerritorium
)
