package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.KachelBlockiertException
import de.github.dudrie.hamster.core.exception.PositionAusserhalbException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

data class BewegeHamsterKommando(private val hamster: InternerHamster) : HamsterKommando() {

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
