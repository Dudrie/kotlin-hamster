package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

class Territory(val hamsterGame: HamsterGame, internal val internalTerritory: GameTerritory) {

    val territorySize: Size = internalTerritory.size

    fun getTileAt(location: Location): GameTile = internalTerritory.getTileAt(location)

    // TODO: Add more helper functions!

}
