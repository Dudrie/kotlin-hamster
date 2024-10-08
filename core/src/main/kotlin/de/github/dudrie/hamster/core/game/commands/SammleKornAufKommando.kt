package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.KeinKornAufKachelException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

/**
 * Lässt den [hamster] ein Korn von seinem Feld aufsammeln.
 */
class SammleKornAufKommando(private val hamster: InternerHamster) : HamsterKommando() {

    /**
     * Der [hamster] sammelt ein Korn von seinem Feld im [territorium] auf.
     *
     * @throws KeinKornAufKachelException Auf dem Feld des Hamsters liegt kein Korn.
     */
    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        if (!territorium.hatInhalt<KornInhalt>(hamster.position)) {
            throw KeinKornAufKachelException(hamster.position, this)
        }

        val kachelinhalt = territorium.getInhalt<KornInhalt>(hamster.position)
        val gegenstand = kachelinhalt.getInventarInhalt()
        val neuerInhalt = kachelinhalt.copy(anzahl = kachelinhalt.anzahl - 1)

        val neuerHamster = hamster.fugeZuInventarHinzu(gegenstand)
        aktualisierterHamster = neuerHamster

        return territorium.ersetzeHamster(hamster, neuerHamster).ersetzeKachelInhalt(
            position = neuerHamster.position,
            neuerInhalt = if (neuerInhalt.anzahl == 0) {
                Leer
            } else {
                neuerInhalt
            }
        )
    }

    override fun getLogNachricht(): HamsterString =
        HamsterString(HamsterStringId.KOMMANDO_HAMSTER_SAMMLE_KORN_AUF, hamster.position)

}
