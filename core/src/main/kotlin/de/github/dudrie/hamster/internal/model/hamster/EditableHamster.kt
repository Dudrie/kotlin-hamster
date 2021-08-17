package de.github.dudrie.hamster.internal.model.hamster

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.internal.model.territory.EditableGameTile

/**
 * Holds all state related to a hamster in the editor.
 *
 * @param tile Initial tile the hamster is on.
 * @param direction Initial direction the hamster faces in.
 * @param grainCount Initial number of grains in the hamster's mouth.
 */
class EditableHamster(tile: EditableGameTile, direction: Direction, grainCount: Int = 0) :
    HamsterTileContent(tile, direction, grainCount) {

}
