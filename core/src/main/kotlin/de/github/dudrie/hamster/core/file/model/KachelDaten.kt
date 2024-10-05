package de.github.dudrie.hamster.core.file.model

import de.github.dudrie.hamster.core.model.kachel.Kachelinhalt
import de.github.dudrie.hamster.core.model.util.Position
import kotlinx.serialization.Serializable

/**
 * Daten einer [de.github.dudrie.hamster.core.model.kachel.Kachel] zum Speichern in einer Datei.
 *
 * @param position [Position] der Kachel.
 * @param inhalt [Kachelinhalt] der Kachel.
 */
@Serializable
data class KachelDaten(val position: Position, val inhalt: Kachelinhalt)
