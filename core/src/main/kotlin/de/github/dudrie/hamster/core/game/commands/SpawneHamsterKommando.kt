package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.KachelBlockiertException
import de.github.dudrie.hamster.core.exception.PositionAusserhalbException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

/**
 * Setzt den [hamster] in ein Territorium.
 */
data class SpawneHamsterKommando(private val hamster: InternerHamster) : Kommando() {

    /**
     * Setzt den [hamster] in das [territorium].
     *
     * @throws PositionAusserhalbException Die [Position des Hamsters][InternerHamster.position] liegt außerhalb des [territorium].
     * @throws KachelBlockiertException Die Kachel, auf die der Hamster gesetzt werden soll, ist blockiert.
     */
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
        HamsterString(HamsterStringId.KOMMANDO_TERRITORIUM_SPAWNE_HAMSTER, hamster.position)

}
