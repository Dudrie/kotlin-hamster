package de.github.dudrie.hamster.ui.helpers

import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.R

/**
 * Returns the path to the resource associated with this [GameTileType].
 */
fun GameTileType.getResource() = when (this) {
    GameTileType.Wall -> R.images.wall
    GameTileType.Floor -> R.images.floor
}
