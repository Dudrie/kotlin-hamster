package de.github.dudrie.hamster.importer.data

import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Data to generate a tile upon initialization.
 *
 * @param location [Location] this tile will be at.
 * @param tileType Type of the tile. Defaults to [GameTileType.Floor].
 * @param grainCount Initial amount of grains on this tile. Default to `0`.
 */
data class TileData(val location: Location, val tileType: GameTileType = GameTileType.Floor, val grainCount: Int = 0)
