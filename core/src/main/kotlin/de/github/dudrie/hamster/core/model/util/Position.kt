package de.github.dudrie.hamster.core.model.util

import kotlinx.serialization.Serializable

/**
 * Beschreibt eine Position im Territorium.
 *
 * Dabei ist (0, 0) die obere, linke Kachel.
 *
 * @param x Die Nummer der Spalte.
 * @param y Die Nummer der Zeile.
 */
@Serializable
data class Position(val x: Int, val y: Int) {

    /**
     * Gibt die [Position] nach der [bewegung] zurück.
     */
    operator fun plus(bewegung: Bewegung): Position = copy(
        x = x + bewegung.deltaX,
        y = y + bewegung.deltaY
    )

    override fun toString(): String = "($x,$y)"
}
