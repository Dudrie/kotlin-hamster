package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.*
import de.github.dudrie.hamster.execptions.GameAbortedException
import java.util.concurrent.Semaphore

class GameCommandStack : CommandStack() {
    companion object {
        const val minSpeed = 1f
        const val maxSpeed = 10f
        const val speedSteps = (maxSpeed - minSpeed - 1).toInt()
        val speedRange = minSpeed..maxSpeed
    }

    private val speedState = mutableStateOf(4.0f)
    val speed: Float by speedState

    private val modeState = mutableStateOf(GameMode.Initializing)
    val mode: GameMode by modeState

    private val isUndoingOrRedoingState = mutableStateOf(false)
    private var isUndoingOrRedoing: Boolean by isUndoingOrRedoingState

    private val runtimeExceptionState = mutableStateOf<RuntimeException?>(null)
    val runtimeException: RuntimeException? by runtimeExceptionState

    private val gameLog: GameLog = GameLog()
    val gameMessages: Iterable<String>
        get() = gameLog.messages

    private val pauseLock: Semaphore = Semaphore(1, true)

    @Composable
    fun canUndoCommand(): State<Boolean> =
        rememberUpdatedState(hasCommandsToUndo.value && mode == GameMode.Paused && !isUndoingOrRedoing)

    @Composable
    fun canRedoCommand(): State<Boolean> =
        rememberUpdatedState(hasCommandsToRedo.value && mode == GameMode.Paused && !isUndoingOrRedoing)

    @Composable
    fun canPauseOrResumeGame(): State<Boolean> =
        rememberUpdatedState(mode == GameMode.Running || mode == GameMode.Paused && !isUndoingOrRedoing)

    fun setSpeed(speed: Float) {
        require(speed in speedRange) { "Speed must be between $minSpeed and $maxSpeed." }
        speedState.value = speed
    }

    fun executeCommand(command: Command) {
        try {
            pauseLock.acquire()
            execute(command)
        } catch (e: InterruptedException) {
        } finally {
            pauseLock.release()
        }
    }

    override fun execute(command: Command) {
        executionLock.lock()
        try {
            checkCommandCanBeExecuted(command)

            try {
                super.execute(command)
                gameLog.addMessage(command.getCommandLogMessage())
            } catch (e: Exception) {
                modeState.value = GameMode.Stopped
                throw e
            }
        } catch (e: RuntimeException) {
            runtimeExceptionState.value = e
            println(e)
            stopGame()
        } finally {
            executionLock.unlock()
        }
        delayNextCommand()
    }

    override fun undo() {
        executionLock.lock()
        try {
            require(mode == GameMode.Stopped || mode == GameMode.Paused) { "One can only undo a command if the game is paused or stopped." }
            isUndoingOrRedoing = true
            super.undo()
            gameLog.removeLastMessage()
        } finally {
            isUndoingOrRedoing = false
            executionLock.unlock()
        }
    }

    override fun redo() {
        executionLock.lock()
        try {
            require(mode == GameMode.Stopped || mode == GameMode.Paused) { "One can only redo a command if the game is paused or stopped." }
            isUndoingOrRedoing = true
            super.redo()
        } finally {
            isUndoingOrRedoing = false
            executionLock.unlock()
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
            redoAll()
            modeState.value = GameMode.Running
        } finally {
            executionLock.unlock()
        }
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
        when (mode) {
            GameMode.Aborted -> {
                modeState.value = GameMode.Stopped
                throw GameAbortedException("One cannot run commands on an aborted game.")
            }
            GameMode.Initializing -> {
                throw IllegalStateException("One cannot run commands if the game is still initializing.")
            }
            GameMode.Stopped -> {
                throw IllegalStateException("One cannot execute commands if the game is stopped.")
            }
            else -> return
        }
    }

    private fun delayNextCommand() {
        try {
            Thread.sleep(((maxSpeed + 1 - speed) / 5.0 * 400.0).toLong())
        } catch (e: InterruptedException) {
        }
    }
}
