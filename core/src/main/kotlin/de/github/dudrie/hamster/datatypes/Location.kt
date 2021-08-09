package de.github.dudrie.hamster.datatypes

data class Location(val column: Int, val row: Int) {
    companion object {
        val Origin: Location = Location(0, 0)
    }

    init {
        require(column >= 0) { "Column must be zero or positive." }
        require(row >= 0) { "Row must be zero or positive" }
    }

    override fun toString(): String {
        return "(col: $column, row: $row)"
    }
}

