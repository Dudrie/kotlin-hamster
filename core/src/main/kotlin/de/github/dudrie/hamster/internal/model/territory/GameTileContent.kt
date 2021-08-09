package de.github.dudrie.hamster.internal.model.territory

enum class GameTileContentType {
    Hamster, Grain
}

abstract class GameTileContent(val type: GameTileContentType) {
    abstract val currentTile: GameTile

    abstract val isBlocking: Boolean
}
