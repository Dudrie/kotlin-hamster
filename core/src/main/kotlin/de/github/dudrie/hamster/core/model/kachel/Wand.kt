package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

/**
 * Beschreibt eine Wand.
 */
@Serializable
data object Wand : Kachelinhalt() {

    override val blocktBewegung: Boolean = true

}
