package de.gothub.dudrie.hamster.importer.data

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location

data class HamsterData(val location: Location, val direction: Direction, val grainCount: Int)
