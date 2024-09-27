package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

@Serializable
class Wand : Kachelinhalt() {
    override val blocktBewegung: Boolean = true
}
