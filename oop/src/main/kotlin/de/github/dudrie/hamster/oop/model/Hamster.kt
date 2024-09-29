package de.github.dudrie.hamster.oop.model

import de.github.dudrie.hamster.core.game.commands.*
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.hamster.InventarInhalt
import de.github.dudrie.hamster.core.model.hamster.Korn
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung

class Hamster(val territorium: Territorium, ort: Position, richtung: Richtung, kornAnzahl: Int) {

    private var internerHamster: InternerHamster

    private val spiel = territorium.spiel

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

    val anzahlKorner: Int
        get() = internerHamster.kornAnzahl

    val ort: Position
        get() = internerHamster.position

    val richtung: Richtung
        get() = internerHamster.richtung

    // TODO: Gemachte Schritte + Umrechnung in "Meter/Zentimeter"

    fun laufe() {
        territorium.runKommandoUndUpdate {
            val kommando = BewegeHamsterKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    fun dreheNachLinks() {
        territorium.runKommandoUndUpdate {
            val kommando = DreheLinksKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    fun sammleKornAuf() {
        territorium.runKommandoUndUpdate {
            val kommando = SammleKornAufKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    fun legeKornAb() {
        territorium.runKommandoUndUpdate {
            val kommando = LegeKornAbKommando(internerHamster)
            spiel.fuhreAus(kommando)
            internerHamster = kommando.aktualisierterHamster
        }
    }

    fun istVorDirFrei(): Boolean =
        !territorium.istBlockiert(internerHamster.getPositionNachSchritt())

    fun liegtEinKornAufDeinemFeld(): Boolean = territorium.getAnzahlKornerAufFeld(ort) > 0

    fun istDeinMundLeer(): Boolean = anzahlKorner == 0

    fun sage(nachricht: String) {
        territorium.runKommandoUndUpdate {
            spiel.fuhreAus(SageEtwasKommando(nachricht))
        }
    }
}
