package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.hamster.GameHamster

class PickGrainCommand(private val hamster: GameHamster) : Command() {
    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun undo() {
        TODO("Not yet implemented")
    }

    override fun getExceptionsFromCommandExecution(): List<RuntimeException> {
        TODO("Not yet implemented")
    }
}
