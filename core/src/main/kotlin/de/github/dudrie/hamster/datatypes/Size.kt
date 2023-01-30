package de.github.dudrie.hamster.datatypes

/**
 * Represents the size of a [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory].
 *
 * @param columnCount Amount of columns the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory] has. Must be zero or greater.
 * @param rowCount Amount of row the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory] has. Must be zero or greater.
 */
data class Size(val columnCount: Int, val rowCount: Int) {

    /**
     * Checks if the given [HamsterLocation] is inside the boundaries of this [Size].
     *
     * @return `true` if the given [HamsterLocation] is inside the boundaries of this [Size].
     */
    fun isLocationInside(location: HamsterLocation): Boolean = location.row < rowCount && location.column < columnCount

    /**
     * Creates an [Iterator] of [Locations][HamsterLocation] which are inside this [Size].
     *
     * [Locations][HamsterLocation] are iterated from the left to right and top to bottom starting in the upper left corner.
     *
     * @return [Iterator] as described above.
     */
    fun getAllLocationsInside(): Iterator<HamsterLocation> {
        return object : Iterator<HamsterLocation> {
            private var currentCol = 0
            private var currentRow = 0

            override fun hasNext(): Boolean = currentRow < rowCount

            override fun next(): HamsterLocation {
                val location = HamsterLocation(currentCol, currentRow)
                currentCol += 1

                if (currentCol >= columnCount) {
                    currentCol = 0
                    currentRow++
                }

                return location
            }
        }
    }

    /**
     * @return String representation of this [Size].
     */
    override fun toString(): String {
        return "(cols: $columnCount, rows: $rowCount)"
    }
}
