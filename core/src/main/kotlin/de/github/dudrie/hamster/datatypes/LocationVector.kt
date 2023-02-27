package de.github.dudrie.hamster.datatypes

/**
 * Represents a mathematical vector which can be used to translate a [SpielOrt].
 *
 * @param columnDelta Value representing the translation in the column direction.
 * @param rowDelta Value representing the translation in the row direction.
 */
data class LocationVector(val columnDelta: Int, val rowDelta: Int)
