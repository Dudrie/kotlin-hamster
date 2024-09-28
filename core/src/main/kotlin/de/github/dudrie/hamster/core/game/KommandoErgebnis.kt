package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.game.commands.Kommando
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium

data class KommandoErgebnis(
    val kommando: Kommando,
    val territoriumVorher: InternesTerritorium,
    val territoriumNachher: InternesTerritorium
)
