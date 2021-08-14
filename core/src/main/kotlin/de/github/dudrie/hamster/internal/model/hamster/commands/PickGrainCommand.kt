package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.execptions.NoGrainsOnTileException
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile

class PickGrainCommand(private val hamster: GameHamster) : Command() {
    private var tileGrainWasPickedFrom: GameTile? = null

    override fun execute() {
        require(canBeExecuted()) { "Pick grain command cannot be executed." }
        val tile = hamster.currentTile
        tile.removeGrainFromTile()
        hamster.pickGrain()
        tileGrainWasPickedFrom = tile
    }

    override fun undo() {
        tileGrainWasPickedFrom?.let {
            hamster.dropGrain()
            it.addGrainToTile()
            tileGrainWasPickedFrom = null
        }
    }

    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        if (hamster.currentTile.grainCount <= 0) {
            return listOf(NoGrainsOnTileException(hamster.currentTile))
        }
        return listOf()
    }

    override fun getCommandLogMessage(): String {
        val tile = tileGrainWasPickedFrom
        return if (tile != null) {
            ResString.getWithFormat("command.pick.grain.log.on.location", tile.location)
        } else {
            ResString.get("command.pick.grain.log.no.location.given")
        }
    }
}
