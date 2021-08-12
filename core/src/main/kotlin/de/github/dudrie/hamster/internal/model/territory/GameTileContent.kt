package de.github.dudrie.hamster.internal.model.territory

enum class GameTileContentType {
    Hamster
}

abstract class GameTileContent(val type: GameTileContentType) {
    abstract val currentTile: GameTile

    abstract val isBlockingMovement: Boolean
}
