package de.github.dudrie.hamster.core.model.kachel

import kotlinx.serialization.Serializable

/**
 * Beschreibt den Inhalt einer [Kachel].
 */
@Serializable
sealed class Kachelinhalt {

    /**
     * Blockiert dieser [Kachelinhalt] die Bewegung des [Hamsters][de.github.dudrie.hamster.core.model.hamster.InternerHamster]?
     */
    abstract val blocktBewegung: Boolean

}

