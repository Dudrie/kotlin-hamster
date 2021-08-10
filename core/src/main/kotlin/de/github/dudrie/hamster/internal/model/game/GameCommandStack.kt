package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import de.github.dudrie.hamster.execptions.GameAbortedException
import java.util.concurrent.Semaphore

class GameCommandStack : CommandStack() {
    private val _speedState = mutableStateOf(4.0)
    val speedState = produceState(_speedState.value) { _speedState }

    private val _modeState = mutableStateOf(GameMode.Initializing)
    val modeState = produceState(_modeState.value) { _modeState }

    private var speed: Double
        get() = _speedState.value
        set(value) {
            require(value > 0 && value <= 10) { "Provided speed ($value) is not between 0 and 10." }
            _speedState.value = value
        }

    private var mode: GameMode
        get() = _modeState.value
        set(value) {
            _modeState.value = value
        }

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
