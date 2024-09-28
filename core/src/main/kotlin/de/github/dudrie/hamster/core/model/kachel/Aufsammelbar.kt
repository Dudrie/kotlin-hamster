package de.github.dudrie.hamster.core.model.kachel

import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import kotlinx.serialization.Serializable

@Serializable
sealed class Aufsammelbar : Kachelinhalt() {

    override val blocktBewegung: Boolean = false

    abstract fun getInventarInhalt(): InventarInhalt

}
