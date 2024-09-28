package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.KeinKornAufKachelException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString

class SammleKornAufKommando(private val hamster: InternerHamster) : HamsterKommando() {

    override var aktualisierterHamster: InternerHamster? = null

    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        if (!territorium.hatInhalt<KornInhalt>(hamster.position)) {
            throw KeinKornAufKachelException(hamster.position, this)
        }

        val kachelinhalt = territorium.getInhalt<KornInhalt>(hamster.position)
        val gegenstand = kachelinhalt.getInventarInhalt()
        val neuerInhalt = kachelinhalt.copy(anzahl = kachelinhalt.anzahl - 1)

        val neuerHamster = hamster.fugeZuInventarHinzu(gegenstand)
        aktualisierterHamster = neuerHamster

        val neuesTerritorium = territorium.ersetzeHamster(hamster, neuerHamster)
        return neuesTerritorium.ersetzeKachelInhalt(
            position = neuerHamster.position,
            neuerInhalt = if (neuerInhalt.anzahl == 0) {
                Leer
            } else {
                neuerInhalt
            }
        )
    }

    override fun getLogNachricht(): HamsterString = HamsterString("hamster.kommando.sammle.korn")

}
