package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

/**
 * Dreht den [hamster] um 90 Grad gegen den Uhrzeigersinn.
 */
data class DreheLinksKommando(private val hamster: InternerHamster) : HamsterKommando() {

    /**
     * Dreht den [hamster] im [territorium] um 90 Grad gegen den Uhrzeigersinn.
     */
    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        val gedrehterHamster = hamster.dreheDichNach(hamster.richtung.nachLinksGedreht())
        aktualisierterHamster = gedrehterHamster

        return territorium.ersetzeHamster(hamster, gedrehterHamster)
    }

    override fun getLogNachricht(): HamsterString =
        HamsterString(HamsterStringId.KOMMANDO_HAMSTER_DREHE_LINKS, hamster.position)

}
