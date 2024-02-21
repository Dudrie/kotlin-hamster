package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.mutableStateOf
import java.util.concurrent.locks.ReentrantLock

/**
 * Handles the execution of [Commands][Command] without any game context.
 */
abstract class CommandStack {
    /**
     * List of all [Commands][Command] that got successfully executed.
     */
    protected val executedCommands: MutableList<Command> = mutableListOf()

    /**
     * List of all [Commands][Command] that got undone previously.
     */
    protected val undoneCommands: MutableList<Command> = mutableListOf()

    /**
     * Does this stack hold any commands that can be undone?
     */
    val hasCommandsToUndo: Boolean
        get() = executedCommands.size > 0

    /**
     * Does this stack hold any commands that can be redone?
     */
    val hasCommandsToRedo: Boolean
        get() = undoneCommands.size > 0

    /**
     * Sum of all command either executed or undone.
     */
    val commandCount: Int
        get() = executedCommands.size + undoneCommands.size

    /**
     * Lock which is used to ensure that commands are executed in sequence and that no two commands are executed at the same time.
     */
    protected val executionLock: ReentrantLock = ReentrantLock(true)

    /**
     * Executes the given command.
     *
     * This function does **NOT** check whether a [Command] can be executed before executing it.
     *
     * It adjusts the list of [executedCommands] and [hasCommandsToUndo] accordingly after executing the command.
     *
     * @param command [Command] to execute.
     */
    protected open fun execute(command: Command) {
        executionLock.lock()
        try {
            command.execute()
            executedCommands += command
        } finally {
            executionLock.unlock()
        }
    }

    /**
     * Undoes the [Command] that got executed last.
     *
     * There must be a command which has been executed. Adjusts the list of [undoneCommands] and [hasCommandsToUndo] and [hasCommandsToRedo] accordingly.
     */
    open fun undo() {
        executionLock.lock()
        try {
            require(executedCommands.size > 0) { "There are no commands to undo." }
            val commandToUndo = executedCommands.removeLast()
            commandToUndo.undo()

            undoneCommands += commandToUndo
        } finally {
            executionLock.unlock()
        }
    }

    /**
     * Redoes the last [Command] that got undone.
     *
     * The function does **NOT** check whether a [Command] can be redone before executing it. It assumes that any [Command] that was previously successfully executed can be so again and that there are no other commands executed if there are still [Commands][Command] which can be redone.
     *
     * This makes sure that the redone [Command] is back in the list of [executedCommands].
     */
    open fun redo() {
        executionLock.lock()
        try {
            require(undoneCommands.size > 0) { "There are no commands to redo." }
            val commandToRedo = undoneCommands.removeLast()
            execute(commandToRedo)
        } finally {
            executionLock.unlock()
        }
    }

    /**
     * Redoes all previously undone [Commands][Command]
     *
     * @see redo
     */
    protected fun redoAll() {
        executionLock.lock()
        try {
            while (hasCommandsToRedo) {
                redo()
            }
        } finally {
            executionLock.unlock()
        }
    }
}
