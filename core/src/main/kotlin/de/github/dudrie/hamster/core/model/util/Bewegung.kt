package de.github.dudrie.hamster.core.model.util

/**
 * Beschreibt eine Bewegung des Hamsters.
 *
 * @param deltaX Bewegung in X Richtung, d.h. entlang der Spalten des Territoriums.
 * @param deltaY Bewegung in Y Richtung, d.h. entlang der Zeilen des Territoriums.
 */
data class Bewegung(val deltaX: Int, val deltaY: Int)
