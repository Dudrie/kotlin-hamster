package de.gothub.dudrie.hamster.importer.data

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.territory.GameTileType

data class TileData(val location: Location, val tileType: GameTileType = GameTileType.Floor, val grainCount: Int = 0)
