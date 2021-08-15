package de.github.dudrie.hamster.internal.model.game

/**
 * Represents a [Command] to change the state of the game.
 */
abstract class Command {
    /**
     * Logic to execute the command.
     */
    abstract fun execute()

    /**
     * Logic to undo the command if it was executed successfully previously.
     */
    abstract fun undo()

    /**
     * Generates a [List] of [RuntimeExceptions][RuntimeException] that contains all exception which would be thrown if the command gets executed.
     *
     * @return Generated [List].
     */
    abstract fun getExceptionsFromCommandExecution(): List<RuntimeException>

    /**
     * Generates and returns the message for the [GameLog] for this [Command].
     *
     * @return Generated message.
     */
    abstract fun getCommandLogMessage(): String

    /**
     * Checks if the [Command] can be executed.
     *
     * This is done by checking the [List] returned by [getExceptionsFromCommandExecution].
     *
     * @return `true` if the [Command] can be executed.
     */
    fun canBeExecuted(): Boolean {
        return getExceptionsFromCommandExecution().isEmpty()
    }
}
