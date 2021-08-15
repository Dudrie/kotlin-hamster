package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.*
import java.util.concurrent.Semaphore

/**
 * Stack to handle [Command] execution with the game context.
 */
class GameCommandStack : CommandStack() {
    companion object {
        /**
         * Minimum speed configurable for the game.
         */
        const val minSpeed = 1f

        /**
         * Maximum speed configurable for the game.
         */
        const val maxSpeed = 10f

        /**
         * Whole [Int] steps in between [minSpeed] and [maxSpeed] **excluding** both, start and end.
         */
        const val speedSteps = (maxSpeed - minSpeed - 1).toInt()

        /**
         * Helper which generates the [ClosedFloatingPointRange] between [minSpeed] and [maxSpeed].
         */
        val speedRange = minSpeed..maxSpeed
    }

    private val speedState = mutableStateOf(4.0f)

    /**
     * Current speed of the game.
     */
    val speed: Float by speedState

    private val modeState = mutableStateOf(GameMode.Initializing)

    /**
     * Current [GameMode] of the game.
     */
    val mode: GameMode by modeState

    private val isUndoingOrRedoingState = mutableStateOf(false)
    private var isUndoingOrRedoing: Boolean by isUndoingOrRedoingState

    private val runtimeExceptionState = mutableStateOf<RuntimeException?>(null)

    /**
     * Last thrown [RuntimeException] that would have been thrown if the current [Command] would have been executed.
     */
    val runtimeException: RuntimeException? by runtimeExceptionState

    private val gameLog: GameLog = GameLog()

    /**
     * All messages currently available in the [GameLog].
     */
    val gameMessages: Iterable<String>
        get() = gameLog.messages

    private val pauseLock: Semaphore = Semaphore(1, true)

    /**
     * @return Is the game in a state in which a [Command] can be undone?
     */
    @Composable
    fun canUndoCommand(): State<Boolean> =
        rememberUpdatedState(hasCommandsToUndo.value && mode == GameMode.Paused && !isUndoingOrRedoing)

    /**
     * @return Is the game in a state in which a [Command] can be redone?
     */
    @Composable
    fun canRedoCommand(): State<Boolean> =
        rememberUpdatedState(hasCommandsToRedo.value && mode == GameMode.Paused && !isUndoingOrRedoing)

    /**
     * @return Is the game in a state in which it can be [paused][GameMode.Paused] or [resumed][GameMode.Running]?
     */
    @Composable
    fun canPauseOrResumeGame(): State<Boolean> =
        rememberUpdatedState(mode == GameMode.Running || mode == GameMode.Paused && !isUndoingOrRedoing)

    /**
     * Set the speed of the game.
     *
     * @param speed New speed of the game. Must be between [minSpeed] and [maxSpeed].
     */
    fun setSpeed(speed: Float) {
        require(speed in speedRange) { "Speed must be between $minSpeed and $maxSpeed." }
        speedState.value = speed
    }

    /**
     * Tells the stack to execute the given [Command] within the game context if the game is [running][GameMode.Running].
     */
    fun executeCommand(command: Command) {
        try {
            pauseLock.acquire()
            execute(command)
        } catch (e: InterruptedException) {
        } finally {
            pauseLock.release()
        }
    }

    /**
     * Executes the [Command] inside the game's context.
     *
     * It checks whether the [Command] can be executed, first. If so, the command gets actually [executed][CommandStack.execute] and the corresponding message gets added to the [GameLog]. Afterwards the stack waits before the next command can be executed.
     *
     * If the command can not be executed the corresponding [RuntimeException] is saved in [runtimeException] and the game is [stopped][GameMode.Stopped].
     *
     * @param command [Command] which should be executed.
     *
     * @see delayNextCommand
     * @see checkModeAllowsCommandExecution
     * @see CommandStack.execute
     *
     * @throws Exception Any exception that is thrown that is not a [RuntimeException].
     */
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
            println("[GameException] $e")
            stopGame()
        } finally {
            executionLock.unlock()
        }
        delayNextCommand()
    }

    /**
     * Undoes the [Command] that got executed last.
     *
     * It also removes the corresponding message from the [GameLog].
     *
     * The game must not be [paused][GameMode.Paused] nor [stopped][GameMode.Stopped] in order to undo [Commands][Command].
     *
     * @see CommandStack.undo
     */
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

    /**
     * Redoes the [Command] that got undone last.
     *
     * The game must not be [paused][GameMode.Paused] nor [stopped][GameMode.Stopped] in order to redo [Commands][Command].
     *
     * @see CommandStack.redo
     */
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

    /**
     * Starts the game.
     *
     * To start a game the game must be [initializing][GameMode.Initializing]. After executing this function the [GameCommandStack] is ready to take in [Commands][Command] to [execute].
     *
     * @param startPaused If `true` the game gets fully initialized but will then be moved into the [paused mode][GameMode.Paused] instead of the [running mode][GameMode.Running].
     */
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

    private fun stopGame() {
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

    /**
     * Pauses the game setting the mode to [paused][GameMode.Paused].
     *
     * This prevents all [Command] execution until [resumeGame] is called. The game must be [running][GameMode.Running] to be [paused][GameMode.Paused].
     */
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

    /**
     * Resumes the game back into it's [running mode][GameMode.Running].
     *
     * The game must be [paused][GameMode.Paused] to be resumed.
     */
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
