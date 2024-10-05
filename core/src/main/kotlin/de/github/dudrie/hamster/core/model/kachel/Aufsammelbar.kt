package de.github.dudrie.hamster.core.model.kachel

import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import kotlinx.serialization.Serializable

/**
 * Beschreibt einen aufsammelbaren [Kachelinhalt].
 */
@Serializable
sealed class Aufsammelbar : Kachelinhalt() {

    override val blocktBewegung: Boolean = false

    /**
     * Erzeugt den zu diesem aufsammelbaren gehörenden [InventarInhalt].
     */
    abstract fun getInventarInhalt(): InventarInhalt

}
