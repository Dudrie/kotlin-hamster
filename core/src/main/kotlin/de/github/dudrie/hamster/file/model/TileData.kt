package de.github.dudrie.hamster.file.model

import de.github.dudrie.hamster.datatypes.HamsterOrt
import de.github.dudrie.hamster.internal.model.territory.GameTileType

/**
 * Data to generate a tile upon initialization.
 *
 * @param location [HamsterOrt] this tile will be at.
 * @param tileType Type of the tile. Defaults to [GameTileType.Floor].
 * @param grainCount Initial amount of grains on this tile. Default to `0`.
 * @param hideGrainCount If `true` the tile should hide its grain count and only show if it has at least one grain or zero.
 */
data class TileData(
    val location: HamsterOrt,
    val tileType: GameTileType,
    val grainCount: Int,
    val hideGrainCount: Boolean
)
