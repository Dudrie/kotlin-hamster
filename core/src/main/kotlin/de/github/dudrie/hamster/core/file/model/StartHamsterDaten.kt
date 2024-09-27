package de.github.dudrie.hamster.core.file.model

import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung
import kotlinx.serialization.Serializable

@Serializable
data class StartHamsterDaten(
    val position: Position,
    val richtung: Richtung,
    val inventar: List<InventarInhalt>
)
