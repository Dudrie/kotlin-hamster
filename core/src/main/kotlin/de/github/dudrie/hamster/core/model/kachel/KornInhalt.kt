package de.github.dudrie.hamster.core.model.kachel

import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import de.github.dudrie.hamster.core.model.hamster.Korn
import kotlinx.serialization.Serializable

/**
 * [Aufsammelbarer][Aufsammelbar] [Kachelinhalt], der beschreibt, dass Körner auf dieser [Kachel] liegen.
 *
 * @param anzahl Die Anzahl der Körner auf dieser [Kachel].
 * @param verbergeKornAnzahl Soll die Anzahl der Körner dieser [Kachel] beim Anzeigen verborgen werden?
 */
@Serializable
data class KornInhalt(val anzahl: Int, val verbergeKornAnzahl: Boolean = false) : Aufsammelbar() {

    /**
     * Erzeugt ein [Korn].
     *
     * @throws RuntimeException Der Inhalt ist leer, d.h. die [anzahl] ist 0 (oder weniger).
     */
    override fun getInventarInhalt(): InventarInhalt {
        require(anzahl > 0) { "ERR_NO_GRAIN_ON_TILE" }
        return Korn()
    }

}
