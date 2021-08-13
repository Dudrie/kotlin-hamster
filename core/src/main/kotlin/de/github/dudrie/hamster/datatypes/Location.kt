package de.github.dudrie.hamster.datatypes

data class Location(val column: Int, val row: Int) {
    init {
        require(column >= 0) { "Column must be zero or positive." }
        require(row >= 0) { "Row must be zero or positive" }
    }

    fun translate(vector: LocationVector): Location =
        Location(column = column + vector.columnDelta, row = row + vector.rowDelta)

    override fun toString(): String {
        return "(col: $column, row: $row)"
    }
}
