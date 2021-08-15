package de.github.dudrie.hamster.interfaces

interface IHamster {
    /**
     * Moves the hamster one step.
     */
    fun move()

    /**
     * Turns the hamster 90 degrees counterclockwise.
     */
    fun turnLeft()

    /**
     * Picks up a grain from the tile the hamster currently stands on.
     */
    fun pickGrain()

    /**
     * Drops a grain unto the tile the hamster currently stand on.
     */
    fun dropGrain()
}
