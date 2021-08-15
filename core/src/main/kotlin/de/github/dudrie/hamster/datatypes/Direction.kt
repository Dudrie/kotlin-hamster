package de.github.dudrie.hamster.datatypes

/**
 * Represents a direction in the simulator.
 *
 * Directions in the game use compass directions and are mainly used to describe the direction of view of the [GameHamster][de.github.dudrie.hamster.internal.model.hamster.GameHamster].
 *
 * @param directionVector [LocationVector] that describes the associated movement.
 */
enum class Direction(val directionVector: LocationVector) {
    /**
     * Direction towards the top of the game board.
     */
    North(LocationVector(0, -1)),

    /**
     * Direction towards the right of the game board.
     */
    East(LocationVector(1, 0)),

    /**
     * Direction towards the bottom of the game board.
     */
    South(LocationVector(0, 1)),

    /**
     * Direction towards the left of the game board.
     */
    West(LocationVector(-1, 0));

    /**
     * Returns [Direction] turned 90 degrees counterclockwise.
     *
     * @return [Direction] turned 90 degrees counterclockwise.
     */
    fun left(): Direction {
        return when (this) {
            North -> West
            East -> North
            South -> East
            West -> South
        }
    }
}
