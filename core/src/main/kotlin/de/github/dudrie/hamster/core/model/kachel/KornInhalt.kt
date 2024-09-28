package de.github.dudrie.hamster.core.model.kachel

import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import de.github.dudrie.hamster.core.model.hamster.Korn
import kotlinx.serialization.Serializable

@Serializable
data class KornInhalt(val anzahl: Int, val verbergeKornAnzahl: Boolean = false) : Aufsammelbar() {

    override fun getInventarInhalt(): InventarInhalt {
        require(anzahl > 0) { "ERR_NO_GRAIN_ON_TILE" }
        return Korn()
    }

}
