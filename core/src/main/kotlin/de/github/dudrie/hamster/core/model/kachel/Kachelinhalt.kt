package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

@Serializable
sealed class Kachelinhalt {

    abstract val blocktBewegung: Boolean

}

