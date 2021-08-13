package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.mutableStateOf
import java.util.concurrent.locks.ReentrantLock

abstract class CommandStack {
    protected val executedCommands: MutableList<Command> = mutableListOf()

    protected val undoneCommands: MutableList<Command> = mutableListOf()

    protected val hasCommandsToUndo = mutableStateOf(false)

    protected val hasCommandsToRedo = mutableStateOf(false)

    protected val executionLock: ReentrantLock = ReentrantLock(true)

    protected open fun execute(command: Command) {
        executionLock.lock()
        try {
            command.execute()
            executedCommands += command
            hasCommandsToUndo.value = true
        } finally {
            executionLock.unlock()
        }
    }

    open fun undo() {
        executionLock.lock()
        try {
            require(executedCommands.size > 0) { "There are no commands to undo." }
            val commandToUndo = executedCommands.removeLast()
            commandToUndo.undo()

            undoneCommands += commandToUndo
            hasCommandsToUndo.value = executedCommands.size > 0
            hasCommandsToRedo.value = true
        } finally {
            executionLock.unlock()
        }
    }

    private fun redo() {
        executionLock.lock()
        try {
            require(undoneCommands.size > 0) { "There are no commands to redo." }
            val commandToRedo = undoneCommands.removeLast()
            execute(commandToRedo)

            hasCommandsToUndo.value = executedCommands.size > 0
            hasCommandsToRedo.value = undoneCommands.size > 0
        } finally {
            executionLock.unlock()
        }
    }

    private fun undoAll() {
        executionLock.lock()
        try {
            while (hasCommandsToUndo.value) {
                undo()
            }
        } finally {
            executionLock.unlock()
        }
    }

    protected fun redoAll() {
        executionLock.lock()
        try {
            while (hasCommandsToRedo.value) {
                redo()
            }
        } finally {
            executionLock.unlock()
        }
    }
}
