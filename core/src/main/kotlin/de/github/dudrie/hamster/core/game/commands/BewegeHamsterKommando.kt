package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.KachelBlockiertException
import de.github.dudrie.hamster.core.exception.PositionAusserhalbException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString

data class BewegeHamsterKommando(private val hamster: InternerHamster) : HamsterKommando() {

    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        val neuerOrt = hamster.getPositionNachSchritt()
        if (!territorium.istPositionInnerhalb(neuerOrt)) {
            throw PositionAusserhalbException(hamster.position, this)
        }

        if (territorium.istBlockiert(neuerOrt)) {
            throw KachelBlockiertException(hamster.position, this)
        }

        val bewegterHamster = hamster.laufe()
        aktualisierterHamster = bewegterHamster

        return territorium.ersetzeHamster(hamster, bewegterHamster)
    }

    override fun getLogNachricht(): HamsterString = HamsterString("kommando.hamster.laufe")

}
