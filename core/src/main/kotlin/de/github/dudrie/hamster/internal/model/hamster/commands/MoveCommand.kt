package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.execptions.DestinationOutsideTerritoryException
import de.github.dudrie.hamster.execptions.PathBlockedException
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile

class MoveCommand(private val hamster: GameHamster) : Command() {
    private var oldTile: GameTile? = null

    override fun execute() {
        require(canBeExecuted()) { "Move command cannot be executed." }
        oldTile = hamster.currentTile
        hamster.move()
    }

    override fun undo() {
        oldTile?.let {
            hamster.moveTo(it)
            oldTile = null
        }
    }

    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        val list = mutableListOf<RuntimeException>()

        try {
            val newLocation = hamster.getLocationAfterMove()

            if (!hamster.territory.isLocationInside(newLocation)) {
                list += DestinationOutsideTerritoryException(newLocation)
            } else {
                val newTile = hamster.territory.getTileAt(newLocation)
                if (newTile.blocked) {
                    list += PathBlockedException(newTile)
                }
            }
        } catch (e: RuntimeException) {
            list += e
        }

        return list
    }

    override fun getCommandLogMessage(): String = ResString.get("command.move.log")
}
