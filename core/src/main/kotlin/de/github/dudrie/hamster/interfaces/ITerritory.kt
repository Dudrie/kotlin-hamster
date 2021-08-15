package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Helper interface for the territory used during a game.
 */
interface ITerritory {
    /**
     * [Size] of this territory.
     */
    val territorySize: Size

    /**
     * Returns the [GameTile] at the [location].
     *
     * The [location] must be inside this territory.
     */
    fun getTileAt(location: Location): GameTile

    /**
     * Is the [GameTile] at [location] free for movement?
     */
    fun isFree(location: Location): Boolean

    /**
     * Is the [GameTile] at [location] blocked for movement?
     */
    fun isBlocked(location: Location): Boolean

    /**
     * Returns the number of grains on the [GameTile] at the [location].
     */
    fun getNumbersOfGrainsAt(location: Location): Int


}
