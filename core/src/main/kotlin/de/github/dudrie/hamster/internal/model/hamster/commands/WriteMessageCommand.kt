package de.github.dudrie.hamster.internal.model.hamster.commands

import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack

/**
 * [Command] which prints a [message] to the game's console.
 *
 * In fact this command itself does nothing because the [GameCommandStack] prints the message after "executing" this command and removes the message after "undoing" this command.
 *
 * @param message Message to print.
 */
class WriteMessageCommand(private val message: String) : Command() {

    override fun execute() {
    }

    override fun undo() {
    }

    override fun getExceptionsFromCommandExecution(): List<RuntimeException> = listOf()

    override fun getCommandLogMessage(): String = "Hamster says:\n\"$message\""

}
