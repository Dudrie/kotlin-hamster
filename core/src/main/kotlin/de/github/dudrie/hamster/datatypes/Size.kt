package de.github.dudrie.hamster.datatypes

data class Size(val columnCount: Int, val rowCount: Int) {
    fun isLocationInside(location: Location): Boolean = location.row < rowCount && location.column < columnCount

    override fun toString(): String {
        return "(cols: $columnCount, rows: $rowCount)"
    }
}
