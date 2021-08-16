package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.interfaces.AbstractTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Territory used in the [HamsterGame].
 *
 * @param hamsterGame Hamster game which uses this [Territory].
 * @param internalTerritory Reference to the internal [GameTerritory].
 */
class Territory(val hamsterGame: HamsterGame, internal val internalTerritory: GameTerritory) : AbstractTerritory() {

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

}
