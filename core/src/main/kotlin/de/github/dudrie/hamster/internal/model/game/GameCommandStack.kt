package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.execptions.GameAbortedException
import java.util.concurrent.Semaphore

class GameCommandStack : CommandStack() {
    private val speedState = mutableStateOf(4.0)
    val speed: Double by speedState

    private val modeState = mutableStateOf(GameMode.Initializing)
    val mode: GameMode by modeState

    private val runtimeExceptionState = mutableStateOf<RuntimeException?>(null)
    val runtimeException: RuntimeException? by runtimeExceptionState

    private val pauseLock: Semaphore = Semaphore(1, true)

    override fun execute(command: Command) {
        try {
            pauseLock.acquire()
            executionLock.lock()
            try {
                checkCommandCanBeExecuted(command)

                try {
                    super.execute(command)
                } catch (e: Exception) {
                    modeState.value = GameMode.Stopped
                    throw e
                }
            } catch (e: RuntimeException) {
                runtimeExceptionState.value = e
                stopGame()
            } finally {
                executionLock.unlock()
            }
            delayNextCommand()
        } catch (e: InterruptedException) {
        } finally {
            pauseLock.release()
        }
    }

    fun startGame(startPaused: Boolean) {
        executionLock.lock()
        try {
            require(mode == GameMode.Initializing) { "Game must be initializing to get started." }

            executedCommands.clear()
            undoneCommands.clear()
            hasCommandsToUndo.value = false
            hasCommandsToRedo.value = false

            modeState.value = GameMode.Running

            if (startPaused) {
                pauseGame()
            }
        } finally {
            executionLock.unlock()
        }
    }

    fun stopGame() {
        executionLock.lock()
        try {
            modeState.value = GameMode.Stopped
        } finally {
            executionLock.unlock()
        }
        try {
            pauseLock.acquire()
        } catch (e: InterruptedException) {
        }
    }

    fun pauseGame() {
        executionLock.lock()
        try {
            require(mode == GameMode.Running) { "One can only pause a running game." }
            modeState.value = GameMode.Paused
        } finally {
            executionLock.unlock()
        }

        try {
            pauseLock.acquire()
        } catch (e: InterruptedException) {
        }

    }

    fun resumeGame() {
        executionLock.lock()
        try {
            require(mode == GameMode.Paused) { "One can only resume a paused game." }
        } finally {
            executionLock.unlock()
        }

        redoAll()
        modeState.value = GameMode.Running
        pauseLock.release()
    }

    private fun checkCommandCanBeExecuted(command: Command) {
        checkModeAllowsCommandExecution()
        checkCommandThrowsNoExceptions(command)
        require(mode != GameMode.Stopped) { "Can not executed command because the game is stopped." }
    }

    private fun checkCommandThrowsNoExceptions(command: Command) {
        if (!command.canBeExecuted()) {
            modeState.value = GameMode.Stopped
            throw command.getExceptionsFromCommandExecution()[0]
        }
    }

    private fun checkModeAllowsCommandExecution() {
        if (mode == GameMode.Aborted) {
            modeState.value = GameMode.Stopped
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
