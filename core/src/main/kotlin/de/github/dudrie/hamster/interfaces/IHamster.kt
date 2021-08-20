package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location

/**
 * Helper interface for the hamster used during a game.
 */
interface IHamster {

    /**
     * Current location of the hamster.
     */
    val location: Location

    /**
     * Current direction the hamster is facing.
     */
    val direction: Direction

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

    /**
     * Is the tile in front of the hamster free for movement?
     */
    fun canYouMove(): Boolean

    /**
     * Has the tile the hamster stands on at least one grain?
     */
    fun hasYourTileAGrain(): Boolean

    /**
     * Is the mouth of the hamster empty?
     */
    fun isYourMouthEmpty(): Boolean

    /**
     * Prints a [message] to the game's console.
     */
    fun talk(message: String)

}
