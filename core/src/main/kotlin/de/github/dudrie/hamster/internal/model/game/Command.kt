package de.github.dudrie.hamster.internal.model.game

abstract class Command {
    abstract fun execute()

    abstract fun undo()

    abstract fun getExceptionsFromCommandExecution(): List<RuntimeException>

    fun canBeExecuted(): Boolean {
        return getExceptionsFromCommandExecution().isEmpty()
    }
}
