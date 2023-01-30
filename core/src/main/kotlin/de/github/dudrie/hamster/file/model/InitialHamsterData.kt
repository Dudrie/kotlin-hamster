package de.github.dudrie.hamster.file.model

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.HamsterLocation

/**
 * Data of the hamster used during initialization of the game.
 *
 * @param location Initial [HamsterLocation] of the hamster.
 * @param direction Initial [Direction] the hamster faces in.
 * @param grainCount Initial amount of grains the hamster has in its mouth.
 */
data class InitialHamsterData(val location: HamsterLocation, val direction: Direction, val grainCount: Int)
