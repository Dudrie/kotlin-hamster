package de.github.dudrie.hamster.datatypes

/**
 * Represents a location in the game world.
 *
 * @param column Column index of the location (zero based). Must be zero or positive.
 * @param row Row index of the location (zero based). Must be zero or positive.
 */
data class Location(val column: Int, val row: Int) {
    companion object {
        /**
         * Location of the board's origin (0, 0).
         */
        val ORIGIN = Location(0, 0)
    }

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
