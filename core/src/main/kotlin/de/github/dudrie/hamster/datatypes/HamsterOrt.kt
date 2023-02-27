package de.github.dudrie.hamster.datatypes

/**
 * Represents a location in the game world.
 *
 * @param column Column index of the location (zero based). Must be zero or positive.
 * @param row Row index of the location (zero based). Must be zero or positive.
 */
data class HamsterOrt(val column: Int, val row: Int) {
    /**
     * Helper companion object.
     */
    companion object {
        /**
         * Location of the board's origin (0, 0).
         */
        val ORIGIN = HamsterOrt(0, 0)
    }

    init {
        require(column >= 0) { "Column must be zero or positive." }
        require(row >= 0) { "Row must be zero or positive" }
    }

    /**
     * Creates a new [HamsterOrt] by translating this [HamsterOrt] with the given [LocationVector].
     *
     * @param vector [LocationVector] used for translation.
     *
     * @return New [HamsterOrt] translated by the given [vector].
     */
    fun translate(vector: LocationVector): HamsterOrt =
        HamsterOrt(column = column + vector.columnDelta, row = row + vector.rowDelta)

    /**
     * @return String representation of this [HamsterOrt].
     */
    override fun toString(): String {
        return "(col: $column, row: $row)"
    }
}
