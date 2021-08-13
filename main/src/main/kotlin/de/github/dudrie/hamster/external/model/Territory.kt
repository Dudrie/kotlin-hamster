package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.importer.InitialGameImporter
import de.github.dudrie.hamster.internal.model.territory.GameTerritory
import de.github.dudrie.hamster.internal.model.territory.GameTile

class Territory private constructor(val hamsterGame: HamsterGame, private val internalTerritory: GameTerritory) {
    companion object {
        fun loadFromImporter(importer: InitialGameImporter, hamsterGame: HamsterGame): Territory =
            Territory(hamsterGame, importer.territory)
    }

    val territorySize: Size = internalTerritory.size

    fun getTileAt(location: Location): GameTile = internalTerritory.getTileAt(location)

    fun getInternalTerritory(): GameTerritory = internalTerritory

    // TODO: Add more helper functions!

}
