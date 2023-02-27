package de.github.dudrie.hamster.file.model

import de.github.dudrie.hamster.datatypes.Richtung
import de.github.dudrie.hamster.datatypes.HamsterOrt

/**
 * Data of the hamster used during initialization of the game.
 *
 * @param location Initial [HamsterOrt] of the hamster.
 * @param direction Initial [Richtung] the hamster faces in.
 * @param grainCount Initial amount of grains the hamster has in its mouth.
 */
data class InitialHamsterData(val location: HamsterOrt, val direction: Richtung, val grainCount: Int)
