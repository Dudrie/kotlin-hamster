package de.github.dudrie.hamster.datatypes

data class Size(val columnCount: Int, val rowCount: Int) {
    fun isLocationInside(location: Location): Boolean = location.row < rowCount && location.column < columnCount

    fun getAllLocationsInside(): Iterator<Location> {
        return object : Iterator<Location> {
            private var currentCol = 0
            private var currentRow = 0

            override fun hasNext(): Boolean = currentRow < rowCount

            override fun next(): Location {
                val location = Location(currentCol, currentRow)
                currentCol += 1

                if (currentCol >= columnCount) {
                    currentCol = 0
                    currentRow++
                }

                return location
            }
        }
    }

    override fun toString(): String {
        return "(cols: $columnCount, rows: $rowCount)"
    }
}
