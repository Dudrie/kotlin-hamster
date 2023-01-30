package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.datatypes.HamsterLocation
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
     * Scaling how many meters one single tile represents.
     */
    abstract val tileToMeterScaling: Double

    /**
     * Returns the [GameTile] at the [location].
     *
     * The [location] must be inside this territory.
     */
    abstract fun getTileAt(location: HamsterLocation): GameTile

    /**
     * Is the [GameTile] at [location] free for movement?
     *
     * The [location] is considered free if it is inside the territory and not blocked for movement.
     */
    fun isFree(location: HamsterLocation): Boolean =
        territorySize.isLocationInside(location) && !getTileAt(location).blocked

    /**
     * Is the [GameTile] at [location] blocked for movement?
     *
     * The [location] must be inside the territory.
     */
    fun isBlocked(location: HamsterLocation): Boolean = getTileAt(location).blocked

    /**
     * Returns the number of grains on the [GameTile] at the [location].
     *
     * The [location] must be inside the territory.
     */
    fun getNumbersOfGrainsAt(location: HamsterLocation): Int = getTileAt(location).grainCount


}
