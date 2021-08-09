package de.github.dudrie.hamster.datatypes

data class Size(val columnCount: Int, val rowCount: Int) {
    override fun toString(): String {
        return "(cols: $columnCount, rows: $rowCount)"
    }
}
