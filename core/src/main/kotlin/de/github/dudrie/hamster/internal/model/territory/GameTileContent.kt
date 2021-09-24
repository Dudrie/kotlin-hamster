package de.github.dudrie.hamster.internal.model.territory

/**
 * Abstraction of all content of a tile.
 */
abstract class GameTileContent {
    /**
     * Current tile this content sits on.
     */
    abstract val tile: GameTile

    /**
     * Does this content prevent movement onto its tile?
     */
    abstract val isBlockingMovement: Boolean
}
