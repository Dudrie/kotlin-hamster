package de.github.dudrie.hamster.file.model

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location

/**
 * Data of the hamster used during initialization of the game.
 *
 * @param location Initial [Location] of the hamster.
 * @param direction Initial [Direction] the hamster faces in.
 * @param grainCount Initial amount of grains the hamster has in its mouth.
 */
data class InitialHamsterData(val location: Location, val direction: Direction, val grainCount: Int)
