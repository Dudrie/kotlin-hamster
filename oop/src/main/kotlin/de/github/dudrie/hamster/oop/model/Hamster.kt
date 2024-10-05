package de.github.dudrie.hamster.oop.model

import de.github.dudrie.hamster.core.game.commands.*
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import de.github.dudrie.hamster.core.model.hamster.Korn
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung

/**
 * Hamster, der im Spiel benutzt wird.
 *
 * Erstellt den zugehörigen [InternerHamster] während der Initialisierung.
 *
 * @param territorium [Territorium] des Hamsters.
 * @param ort [Position] an dem sich der Hamster befindet. Muss innerhalb des [Territoriums][territorium] liegen.
 * @param richtung [Richtung], in die der Hamster anfänglich schaut.
 * @param kornAnzahl Anzahl der Körner, die der Hamster zu Beginn im Mund hat.
 */
class Hamster(val territorium: Territorium, ort: Position, richtung: Richtung, kornAnzahl: Int) {

    /**
     * Referenz zum tatsächlichen [InternerHamster].
     */
    private var internerHamster: InternerHamster

    /**
     * [de.github.dudrie.hamster.core.game.SpielViewModel] des Spiels, zu welchem dieser Hamster gehört.
     */
    private val spiel = territorium.spiel

    /**
     * Erstellt einen Hamster im gegebenen [territorium] und dem gegebenen [hamster] als Referenz.
     */
    internal constructor(territorium: Territorium, hamster: InternerHamster) : this(
        territorium,
        hamster.position,
        hamster.richtung,
        hamster.kornAnzahl
    ) {
        internerHamster = hamster
    }

    init {
        val hamster = territorium.getHamsterBei(ort)

        if (hamster == null) {
            val inventar = mutableListOf<InventarInhalt>()
            repeat(kornAnzahl) { inventar += Korn() }

            internerHamster = InternerHamster(
                position = ort,
                richtung = richtung,
                inventar = inventar
            )

            territorium.setzeHamster(internerHamster)
        } else {
            internerHamster = hamster
        }
    }

    /**
     * Der [Ort][Position] an dem sich dieser Hamster befindet.
     */
    val ort: Position
        get() = internerHamster.position

    /**
     * Die [Richtung] in die dieser Hamster schaut.
     */
    val richtung: Richtung
        get() = internerHamster.richtung

    /**
     * Die Anzahl Schritte, die der Hamster bisher gemacht hat.
     */
    val anzahlSchritte: Int
        get() = internerHamster.anzahlSchritte

    /**
     * Bewegt den Hamster einen Schritt in Blickrichtung.
     *
     * Das Feld vor ihm darf nicht blockiert sein.
     */
    fun laufe() {
        territorium.runKommandoUndUpdate {
            val kommando = BewegeHamsterKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    /**
     * Dreht den Hamster um 90 Grad gegen den Uhrzeigersinn.
     */
    fun dreheNachLinks() {
        territorium.runKommandoUndUpdate {
            val kommando = DreheLinksKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    /**
     * Sammelt ein Korn vom Feld des Hamsters auf.
     *
     * Das Feld muss mindestens ein Korn haben, dass aufgesammelt werden kann.
     */
    fun sammleKornAuf() {
        territorium.runKommandoUndUpdate {
            val kommando = SammleKornAufKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    /**
     * Legt ein Korn auf das Feld des Hamsters.
     *
     * Der Hamster muss mindestens ein Korn im Mund haben.
     *
     * @see istDeinMundLeer Damit kann abgefragt werden, ob der Hamster **keine** Körner im Mund hat.
     */
    fun legeKornAb() {
        territorium.runKommandoUndUpdate {
            val kommando = LegeKornAbKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    /**
     * Ist das Feld vor dem Hamster frei?
     */
    fun istVorDirFrei(): Boolean =
        !territorium.istBlockiert(internerHamster.getPositionNachSchritt())

    /**
     * Liegt auf dem Feld des Hamsters mindestens ein Korn?
     */
    fun liegtEinKornAufDeinemFeld(): Boolean = territorium.getAnzahlKornerAufFeld(ort) > 0

    /**
     * Ist der Mund des Hamsters leer?
     */
    fun istDeinMundLeer(): Boolean = internerHamster.kornAnzahl == 0

    /**
     * Gibt die übergebene [nachricht] auf der Spielekonsole aus.
     */
    fun sage(nachricht: String) {
        territorium.runKommandoUndUpdate {
            spiel.fuhreAus(SageEtwasKommando(nachricht))
        }
    }
}
