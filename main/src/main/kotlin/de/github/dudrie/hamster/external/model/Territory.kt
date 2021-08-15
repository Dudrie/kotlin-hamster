package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.interfaces.ITerritory
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Territory used in the [HamsterGame].
 *
 * @param hamsterGame Hamster game which uses this [Territory].
 * @param internalTerritory Reference to the internal [GameTerritory].
 */
class Territory(val hamsterGame: HamsterGame, internal val internalTerritory: GameTerritory) : ITerritory {

    /**
     * [Size] of this territory.
     */
    override val territorySize: Size = internalTerritory.size

    /**
     * Returns the [GameTile] at the [location].
     *
     * The [location] must be inside this territory.
     */
    override fun getTileAt(location: Location): GameTile = internalTerritory.getTileAt(location)

    /**
     * Is the [GameTile] at [location] free for movement?
     *
     * The [location] is considered free if it is inside the territory and not blocked for movement.
     */
    override fun isFree(location: Location): Boolean =
        internalTerritory.isLocationInside(location) && !isBlocked(location)

    /**
     * Is the [GameTile] at [location] blocked for movement?
     *
     * The [location] must be inside the territory. To check whether any location can be moved upon use [isFree].
     */
    override fun isBlocked(location: Location): Boolean = getTileAt(location).blocked

    /**
     * Returns the number of grains on the [GameTile] at the [location].
     *
     * The [location] must be inside the territory.
     */
    override fun getNumbersOfGrainsAt(location: Location): Int = getTileAt(location).grainCount

}
