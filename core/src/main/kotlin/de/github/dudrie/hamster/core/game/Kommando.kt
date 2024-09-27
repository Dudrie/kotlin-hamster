package de.github.dudrie.hamster.core.game

import de.github.dudrie.hamster.core.exception.KachelBlockiertException
import de.github.dudrie.hamster.core.exception.PositionAusserhalbException
import de.github.dudrie.hamster.core.exception.SpielException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString

sealed class Kommando {

    /**
     * Führt das Kommando aus und gibt das [InternesTerritorium] zurück, welches durch die Ausführung des Kommandos entsteht.
     *
     * @throws SpielException Kann das Kommando nicht ausgeführt werden, wird eine [SpielException] geworfen.
     */
    abstract fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium

    /**
     * Erzeugt einen [HamsterString], welcher dieses Kommando beschreibt.
     */
    abstract fun getLogNachricht(): HamsterString

}

data class SpawneHamsterKommando(private val hamster: InternerHamster) : Kommando() {
    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        if (!territorium.istPositionInnerhalb(hamster.position)) {
            throw PositionAusserhalbException(hamster.position, this)
        }

        if (territorium.istBlockiert(hamster.position)) {
            throw KachelBlockiertException(hamster.position, this)
        }

        return territorium.spawneHamster(hamster)
    }

    override fun getLogNachricht(): HamsterString =
        HamsterString("kommando.spawn.hamster", hamster.position)

}
