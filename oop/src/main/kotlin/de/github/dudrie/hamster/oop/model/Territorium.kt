package de.github.dudrie.hamster.oop.model

import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.core.game.commands.SpawneHamsterKommando
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

class Territorium(
    internal val spiel: SpielViewModel,
    private var internesTerritorium: InternesTerritorium
) {

    fun istBlockiert(position: Position): Boolean = internesTerritorium.istBlockiert(position)

    fun getAnzahlKornerAufFeld(position: Position): Int {
        val kachel = internesTerritorium.getKachelBei(position)

        return (kachel.inhalt as? KornInhalt)?.anzahl ?: 0
    }

    internal fun getHamsterBei(position: Position): InternerHamster? =
        internesTerritorium.getHamsterBei(position)

    internal fun setzeHamster(hamster: InternerHamster) {
        runKommandoUndUpdate {
            spiel.fuhreAus(SpawneHamsterKommando(hamster))
        }
    }

    internal fun runKommandoUndUpdate(block: suspend CoroutineScope.() -> Unit) {
        runBlocking {
            block()
            internesTerritorium = spiel.territorium
        }
    }
}