package de.github.dudrie.hamster.internal.model.game

abstract class Command {
    abstract fun execute()

    abstract fun undo()

    abstract fun getExceptionsFromCommandExecution(): List<RuntimeException>

    abstract fun getCommandLogMessage(): String

    fun canBeExecuted(): Boolean {
        return getExceptionsFromCommandExecution().isEmpty()
    }
}
