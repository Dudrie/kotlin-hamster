package de.github.dudrie.hamster.datatypes

/**
 * Represents a location in the game world.
 *
 * @param column Column index of the location (zero based).
 * @param row Row index of the location (zero based).
 */
data class Location(val column: Int, val row: Int) {
    init {
        require(column >= 0) { "Column must be zero or positive." }
        require(row >= 0) { "Row must be zero or positive" }
    }

    /**
     * Creates a new [Location] by translating this [Location] with the given [LocationVector].
     *
     * @param vector [LocationVector] used for translation.
     *
     * @return New [Location] translated by the given [vector].
     */
    fun translate(vector: LocationVector): Location =
        Location(column = column + vector.columnDelta, row = row + vector.rowDelta)

    /**
     * @return String representation of this [Location].
     */
    override fun toString(): String {
        return "(col: $column, row: $row)"
    }
}
