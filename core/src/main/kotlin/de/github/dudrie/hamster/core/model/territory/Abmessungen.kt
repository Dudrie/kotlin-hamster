package de.github.dudrie.hamster.core.model.territory

import de.github.dudrie.hamster.core.model.util.Position

/**
 * Beschreibt die Größe eines Territoriums.
 *
 * @param breite Die Breite (Anzahl Spalten) des Territoriums.
 * @param hohe Die Höhe (Anzahl Zeilen) des Territoriums.
 */
data class Abmessungen(val breite: Int, val hohe: Int) {

    /**
     * Befindet sich die [position] innerhalb dieser [Abmessungen]?
     */
    fun istInnerhalb(position: Position): Boolean =
        position.x in 0..breite && position.y in 0..hohe

}
