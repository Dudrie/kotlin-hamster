package de.github.dudrie.hamster.datatypes

enum class Direction(val directionVector: LocationVector) {
    North(LocationVector(0, -1)),
    East(LocationVector(1, 0)),
    South(LocationVector(0, 1)),
    West(LocationVector(0, -1));

    fun left(): Direction {
        return when (this) {
            North -> West
            East -> North
            South -> East
            West -> South
        }
    }
}
