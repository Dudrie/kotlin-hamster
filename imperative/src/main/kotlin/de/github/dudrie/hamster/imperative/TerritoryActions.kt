package de.github.dudrie.hamster.imperative

/**
 * Returns the tile to meter scaling of the territory.
 */
fun getTileToMeterScaling(): Double {
    return imperativeGlobalGame?.territorium?.feldZuMeterSkalierung ?: throwGameNotInitializedException()
}
