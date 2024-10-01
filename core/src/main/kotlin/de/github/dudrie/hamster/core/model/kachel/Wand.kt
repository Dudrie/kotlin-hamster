package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

@Serializable
data object Wand : Kachelinhalt() {
    override val blocktBewegung: Boolean = true
}
