package de.github.dudrie.hamster.oop.model

import de.github.dudrie.hamster.core.game.SpielViewModel
import de.github.dudrie.hamster.core.game.commands.SpawneHamsterKommando
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

/**
 * Territorium, welches im [Spiel][SpielViewModel] benutzt wird.
 *
 * @param spiel [SpielViewModel], welches das [Territorium][internesTerritorium] benutzt.
 * @param internesTerritorium Referenz auf das interne [InternesTerritorium].
 */
class Territorium(
    internal val spiel: SpielViewModel,
    private var internesTerritorium: InternesTerritorium
) {

    /**
     * [Abmessungen] des Territoriums.
     */
    val abmessungen: Abmessungen
        get() = internesTerritorium.abmessungen

    /**
     * Wie vielen Metern entspricht eine [de.github.dudrie.hamster.core.model.kachel.Kachel] dieses Territoriums?
     */
    val kachelZuMeterSkalierung: Double
        get() = internesTerritorium.kachelZuMeterSkalierung

    /**
     * Ist das Feld an der [position] blockiert?
     *
     * _Blockiert bedeutet, dass ein Hamster dieses Feld **nicht** betreten kann._
     */
    fun istBlockiert(position: Position): Boolean = internesTerritorium.istBlockiert(position)

    /**
     * Gibt die Anzahl der Körner auf dem Feld an der gegebenen [position] zurück.
     */
    fun getAnzahlKornerAufFeld(position: Position): Int {
        val kachel = internesTerritorium.getKachelBei(position)

        return (kachel.inhalt as? KornInhalt)?.anzahl ?: 0
    }

    /**
     * Gibt den [InternerHamster] zurück, welcher sich auf der gegebenen [position] befindet.
     *
     * Ist dort kein Hamster, so wird `null` zurückgegeben.
     */
    internal fun getHamsterBei(position: Position): InternerHamster? =
        internesTerritorium.getHamsterBei(position)

    /**
     * Platziert den [hamster] im Territorium.
     */
    internal fun setzeHamster(hamster: InternerHamster) {
        runKommandoUndUpdate {
            spiel.fuhreAus(SpawneHamsterKommando(hamster))
        }
    }

    /**
     * Führt den übergebenen [block] aus und speichert das im [spiel] entstandene Territorium als [internesTerritorium] ab.
     */
    internal fun runKommandoUndUpdate(block: suspend CoroutineScope.() -> Unit) {
        runBlocking {
            block()
            internesTerritorium = spiel.territorium
        }
    }
}
