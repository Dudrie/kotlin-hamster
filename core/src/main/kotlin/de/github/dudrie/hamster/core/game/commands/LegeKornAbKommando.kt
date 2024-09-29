package de.github.dudrie.hamster.core.game.commands

import de.github.dudrie.hamster.core.exception.KeineKornerImMundException
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.hamster.Korn
import de.github.dudrie.hamster.core.model.kachel.Kachelinhalt
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.core.model.util.HamsterStringId

class LegeKornAbKommando(private val hamster: InternerHamster) : HamsterKommando() {

    override fun fuhreAus(territorium: InternesTerritorium): InternesTerritorium {
        if (hamster.kornAnzahl == 0) {
            throw KeineKornerImMundException(hamster, this)
        }
        val kachelinhalt =
            territorium.getInhaltOderNeu<KornInhalt>(hamster.position) { KornInhalt(0) }
        val neuerInhalt = kachelinhalt.copy(anzahl = kachelinhalt.anzahl + 1)

        aktualisierterHamster = hamster.entferneEinsAusInventar<Korn>()

        return territorium.ersetzeHamster(hamster, aktualisierterHamster)
            .ersetzeKachelInhalt(hamster.position, neuerInhalt)
    }

    override fun getLogNachricht(): HamsterString =
        HamsterString(HamsterStringId.KOMMANDO_HAMSTER_LEGE_KORN_AB, hamster.position)

}
