package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

@Serializable
data class KornInhalt(val anzahl: Int, val verbergeKornAnzahl: Boolean) : Kachelinhalt() {
    override val blocktBewegung: Boolean = false
}
