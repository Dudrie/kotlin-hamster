package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster

class TurnLeftCommand(private val hamster: GameHamster) : Command() {
    private var oldDirection: Direction? = null

    override fun execute() {
        oldDirection = hamster.direction
        hamster.turnLeft()
    }

    override fun undo() {
        oldDirection?.let {
            hamster.turnTo(it)
            oldDirection = null
        }
    }

    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        return listOf()
    }

    override fun getCommandLogMessage(): String = "Hamster turned left."
}
