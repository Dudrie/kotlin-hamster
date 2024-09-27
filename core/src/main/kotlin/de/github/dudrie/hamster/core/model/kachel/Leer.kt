package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

@Serializable
data object Leer : Kachelinhalt() {
    override val blocktBewegung: Boolean = false
}
