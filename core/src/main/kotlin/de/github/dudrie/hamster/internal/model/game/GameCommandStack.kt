package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.github.dudrie.hamster.execptions.GameAbortedException
import java.util.concurrent.Semaphore

class GameCommandStack : CommandStack() {
    private val speedState = mutableStateOf(4.0)
    private val modeState = mutableStateOf(GameMode.Initializing)

    private var speed: Double by speedState

    private var mode: GameMode by modeState

    private val pauseLock: Semaphore = Semaphore(1, true)

    fun startGame(startPaused: Boolean) {
        executionLock.lock()
        try {
            require(mode == GameMode.Initializing) { "Game must be initializing to get started." }

            executedCommands.clear()
            undoneCommands.clear()
            hasCommandsToUndo.value = false
            hasCommandsToRedo.value = false

            mode = if (startPaused) {
                GameMode.Paused
            } else {
                GameMode.Running
            }
        } finally {
            executionLock.unlock()
        }
    }

    override fun execute(command: Command) {
        try {
            pauseLock.acquire()
            executionLock.lock()
            try {
                checkCommandCanBeExecuted(command)

                try {
                    super.execute(command)
                } catch (e: Exception) {
                    mode = GameMode.Stopped
                    throw e
                }
            } finally {
                executionLock.unlock()
            }
            delayNextCommand()
        } catch (e: InterruptedException) {
        } finally {
            pauseLock.release()
        }
    }

    private fun checkCommandCanBeExecuted(command: Command) {
        checkModeAllowsCommandExecution()
        checkCommandThrowsNoExceptions(command)
        require(mode != GameMode.Stopped) { "Can not executed command because the game is stopped." }
    }

    private fun checkCommandThrowsNoExceptions(command: Command) {
        if (!command.canBeExecuted()) {
            mode = GameMode.Stopped
            throw command.getExceptionsFromCommandExecution()[0]
        }
    }

    private fun checkModeAllowsCommandExecution() {
        if (mode == GameMode.Aborted) {
            mode = GameMode.Stopped
            throw GameAbortedException("Command execution was aborted.")
        } else if (mode != GameMode.Running) {
            throw IllegalStateException("The game must be running in order to execute commands.")
        }
    }

    private fun delayNextCommand() {
        try {
            Thread.sleep(((11.0 - speed) / 5.0 * 400.0).toLong())
        } catch (e: InterruptedException) {
        }
    }
}
