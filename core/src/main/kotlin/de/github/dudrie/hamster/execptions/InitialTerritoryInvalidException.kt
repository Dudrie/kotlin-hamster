package de.github.dudrie.hamster.execptions

/**
 * Indicates that the initially provided [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory] is not valid.
 *
 * @param message Describes why the [GameTerritory][de.github.dudrie.hamster.internal.model.territory.GameTerritory] is invalid. Is passed through to the [RuntimeException].
 */
class InitialTerritoryInvalidException(message: String) : RuntimeException(message)
