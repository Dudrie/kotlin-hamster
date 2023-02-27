package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.SpielOrt
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.interfaces.AbstraktesTerritorium
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Territory used in the [HamsterSpiel].
 *
 * @param spiel Hamster game which uses this [Territorium].
 * @param internalTerritory Reference to the internal [GameTerritory].
 */
class Territorium(val spiel: HamsterSpiel, internal val internalTerritory: GameTerritory) : AbstraktesTerritorium() {

    /**
     * [Size] of this territory.
     */
    override val abmessungen: Size = internalTerritory.size

    /**
     * Meters a single tile in this territory represents.
     */
    override val feldZuMeterSkalierung: Double
        get() = internalTerritory.tileToMeterScaling

    /**
     * Returns the [GameTile] at the [ort].
     *
     * The [ort] must be inside this territory.
     */
    override fun holeFeldBei(ort: SpielOrt): GameTile = internalTerritory.getTileAt(ort)

}
