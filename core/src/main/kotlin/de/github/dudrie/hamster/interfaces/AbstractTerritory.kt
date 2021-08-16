package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Helper abstraction for the territory used during a game or in the editor.
 */
abstract class AbstractTerritory {
    /**
     * [Size] of this territory.
     */
    abstract val territorySize: Size

    /**
     * Returns the [GameTile] at the [location].
     *
     * The [location] must be inside this territory.
     */
    abstract fun getTileAt(location: Location): GameTile

    /**
     * Is the [GameTile] at [location] free for movement?
     *
     * The [location] is considered free if it is inside the territory and not blocked for movement.
     */
    fun isFree(location: Location): Boolean = territorySize.isLocationInside(location) && !getTileAt(location).blocked

    /**
     * Is the [GameTile] at [location] blocked for movement?
     *
     * The [location] must be inside the territory.
     */
    fun isBlocked(location: Location): Boolean = getTileAt(location).blocked

    /**
     * Returns the number of grains on the [GameTile] at the [location].
     *
     * The [location] must be inside the territory.
     */
    fun getNumbersOfGrainsAt(location: Location): Int = getTileAt(location).grainCount


}
