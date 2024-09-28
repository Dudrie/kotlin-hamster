package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString

data class DreheLinksKommando(private val hamster: InternerHamster) : HamsterKommando() {

    override var aktualisierterHamster: InternerHamster? = null

    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        val gedrehterHamster = hamster.dreheDichNach(hamster.richtung.nachLinksGedreht())
        this.aktualisierterHamster = gedrehterHamster

        return territorium.ersetzeHamster(hamster, gedrehterHamster)
    }

    override fun getLogNachricht(): HamsterString = HamsterString("kommando.hamster.dreheLinks")

}
