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

    public override fun setDirection(direction: Direction) {
        super.setDirection(direction)
    }

    public override fun setGrainCount(newCount: Int) {
        super.setGrainCount(newCount)
    }

    /**
     * Sets the tile of this hamster to be the given [tile].
     */
    fun setTile(tile: EditableGameTile) {
        super.setTile(tile)
    }

}
