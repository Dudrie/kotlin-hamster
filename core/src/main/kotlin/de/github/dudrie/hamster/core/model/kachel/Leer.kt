package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

/**
 * Beschreibt eine leere Kachel.
 */
@Serializable
data object Leer : Kachelinhalt() {

    override val blocktBewegung: Boolean = false

}
