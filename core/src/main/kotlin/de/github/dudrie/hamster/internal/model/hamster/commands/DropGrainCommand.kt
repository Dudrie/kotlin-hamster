package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.execptions.MouthEmptyException
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile

class DropGrainCommand(private val hamster: GameHamster) : Command() {
    private var tileGrainWasPutOn: GameTile? = null

    override fun execute() {
        require(canBeExecuted()) { "Drop grain command cannot be executed." }
        val tile = hamster.currentTile
        hamster.dropGrain()
        tile.addGrainToTile()
        tileGrainWasPutOn = tile
    }

    override fun undo() {
        tileGrainWasPutOn?.let {
            hamster.pickGrain()
            it.removeGrainFromTile()
            tileGrainWasPutOn = null
        }
    }

    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        if (hamster.grainCount <= 0) {
            return listOf(MouthEmptyException())
        }
        return listOf()
    }
}
