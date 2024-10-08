package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.KachelBlockiertException
import de.github.dudrie.hamster.core.exception.PositionAusserhalbException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

/**
 * Bewegt den [hamster] um einen Schritt, sofern möglich.
 */
data class BewegeHamsterKommando(private val hamster: InternerHamster) : HamsterKommando() {

    /**
     * Bewegt den [hamster] um einen Schritt in seine [Blickrichtung][InternerHamster.richtung] im [territorium].
     *
     * @throws PositionAusserhalbException Die neue [de.github.dudrie.hamster.core.model.util.Position] liegt außerhalb des [territorium].
     * @throws KachelBlockiertException Die Kachel an der neuen [de.github.dudrie.hamster.core.model.util.Position] ist blockiert.
     */
    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        val neuerOrt = hamster.getPositionNachSchritt()
        if (!territorium.istPositionInnerhalb(neuerOrt)) {
            throw PositionAusserhalbException(hamster.position, this)
        }

        if (territorium.istBlockiert(neuerOrt)) {
            throw KachelBlockiertException(neuerOrt, this)
        }

        val bewegterHamster = hamster.laufe()
        aktualisierterHamster = bewegterHamster

        return territorium.ersetzeHamster(hamster, bewegterHamster)
    }

    override fun getLogNachricht(): HamsterString =
        HamsterString(
            HamsterStringId.KOMMANDO_HAMSTER_LAUFE,
            hamster.position,
            aktualisierterHamster.position
        )

}
