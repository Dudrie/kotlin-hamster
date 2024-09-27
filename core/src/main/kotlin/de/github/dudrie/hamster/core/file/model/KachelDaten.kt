package de.github.dudrie.hamster.core.file.model

import de.github.dudrie.hamster.core.model.kachel.Kachelinhalt
import de.github.dudrie.hamster.core.model.util.Position
import kotlinx.serialization.Serializable

@Serializable
data class KachelDaten(val position: Position, val inhalt: Kachelinhalt)
