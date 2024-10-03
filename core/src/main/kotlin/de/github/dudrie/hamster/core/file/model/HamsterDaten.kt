package de.github.dudrie.hamster.core.file.model

import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung
import kotlinx.serialization.Serializable

/**
 * Daten eines [de.github.dudrie.hamster.core.model.hamster.InternerHamster] zur Speicherung in einer Datei.
 *
 * @param position [Position] des Hamsters.
 * @param richtung [Richtung] des Hamsters.
 * @param inventar Das Inventar des Hamsters.
 */
@Serializable
data class HamsterDaten(
    val position: Position,
    val richtung: Richtung,
    val inventar: List<InventarInhalt>
)
