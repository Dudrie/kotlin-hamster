package de.github.dudrie.hamster.internal.model.game

import androidx.compose.runtime.*
import de.github.dudrie.hamster.execptions.GameException
import de.github.dudrie.hamster.internal.model.hamster.commands.SpawnHamsterCommand
import java.util.concurrent.Semaphore

/**
 * Stack to handle [Command] execution with the game context.
 */
class GameCommandStack : CommandStack() {
    /**
     * Object holding general settings for the game's execution.
     */
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

    private val gameExceptionState = mutableStateOf<GameException?>(null)

    /**
     * Exception that contains a [Command] and the [RuntimeException] that this command would have thrown.
     */
    val gameException: GameException? by gameExceptionState

    private val gameLog: GameLog = GameLog()

    /**
     * All messages currently available in the [GameLog].
     */
    val gameMessages: Iterable<String>
        get() = gameLog.messages

    /**
     * Amount of messages saved in the game log.
     */
    val gameMessageCount: Int
        get() = gameLog.messageCount

    private val pauseLock: Semaphore = Semaphore(1, true)

    /**
     * Is the game in a state in which a [Command] can be undone?
     */
    val canUndoCommand: State<Boolean>
        @Composable
        get() = rememberUpdatedState(hasCommandsToUndo.value && isInModeToUndoOrRedo() && !isUndoingOrRedoing)

    /**
     * Is the game in a state in which a [Command] can be redone?
     */
    val canRedoCommand: State<Boolean>
        @Composable
        get() = rememberUpdatedState(hasCommandsToRedo.value && isInModeToUndoOrRedo() && !isUndoingOrRedoing)

    /**
     * Is the game in a state in which it can be [paused][GameMode.Paused] or [resumed][GameMode.Running]?
     */
    val canPauseOrResumeGame: State<Boolean>
        @Composable
        get() = rememberUpdatedState(mode == GameMode.Running || mode == GameMode.Paused && !isUndoingOrRedoing)

    private fun isInModeToUndoOrRedo(): Boolean =
        mode == GameMode.Paused || mode == GameMode.Stopped || mode == GameMode.Aborted

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
        } catch (_: InterruptedException) {
        } finally {
            pauseLock.release()
        }
    }

    /**
     * Executes the [Command] inside the game's context.
     *
     * It checks whether the [Command] can be executed, first. If so, the command gets actually [executed][CommandStack.execute] and the corresponding message gets added to the [GameLog]. Afterwards the stack waits before the next command can be executed.
     *
     * If the command can not be executed the corresponding [RuntimeException] is saved in [gameException] and the game is [aborted][GameMode.Aborted].
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

                if (mode != GameMode.Initializing) {
                    gameLog.addMessage(command.getCommandLogMessage())
                }
            } catch (e: Exception) {
                abortGame()
                throw e
            }
        } catch (e: RuntimeException) {
            gameExceptionState.value = GameException(e, command)
            println("[GameException] $e")
            abortGame()
        } finally {
            executionLock.unlock()
        }

        // If a command gets executed while the game is stopped or aborted it's because the user is redoing this command. This should happen without a delay.
        if (mode != GameMode.Stopped && mode != GameMode.Aborted) {
            delayNextCommand()
        }
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
            require(isInModeToUndoOrRedo()) { "One can only undo a command if the game is in a mode that allows to undo commands. The current mode $mode does NOT allow undoing commands.." }
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
            require(isInModeToUndoOrRedo()) { "One can only redo a command if the game is in a mode that allows to redo commands. The current mode $mode does NOT allow redoing commands.." }
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

    /**
     * Stops the game.
     *
     * Only a [running][GameMode.Running] can be stopped. A stopped game will stay in this mode forever it cannot be restarted from within the game window. To pause a game use [pauseGame] instead.
     */
    fun stopGame() {
        haltGame(GameMode.Stopped)
    }

    /**
     * Pauses the game setting the mode to [paused][GameMode.Paused].
     *
     * This prevents all [Command] execution until [resumeGame] is called. The game must be [running][GameMode.Running] to be [paused][GameMode.Paused].
     */
    fun pauseGame() {
        haltGame(GameMode.Paused)
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

    /**
     * Aborts the game.
     *
     * This indicates that the game got halted due to an exception during its execution.
     *
     * Only a [running][GameMode.Running] can be stopped. An aborted game will stay in this mode forever it cannot be restarted from within the game window.
     */
    private fun abortGame() {
        executionLock.unlock()
        haltGame(GameMode.Aborted)
    }

    /**
     * Halts the game setting the [mode] to the [newMode].
     *
     * Only a [running][GameMode.Running] game can be halted.
     */
    private fun haltGame(newMode: GameMode) {
        executionLock.lock()
        try {
            require(mode == GameMode.Running) { "One can only change the mode of a running game to $newMode." }
            modeState.value = newMode
        } finally {
            executionLock.unlock()
        }
        try {
            pauseLock.acquire()
        } catch (_: InterruptedException) {
        }
    }

    private fun checkCommandCanBeExecuted(command: Command) {
        checkModeAllowsCommandExecution(command)
        checkCommandThrowsNoExceptions(command)
    }

    private fun checkCommandThrowsNoExceptions(command: Command) {
        if (!command.canBeExecuted()) {
            throw command.getExceptionsFromCommandExecution()[0]
        }
    }

    private fun checkModeAllowsCommandExecution(command: Command) {
        when (mode) {
            GameMode.Initializing -> {
                if (command !is SpawnHamsterCommand) {
                    throw IllegalStateException("One cannot run commands if the game is still initializing.")
                }
            }

            else -> return
        }
    }

    private fun delayNextCommand() {
        try {
            Thread.sleep(((maxSpeed + 1 - speed) / 5.0 * 400.0).toLong())
        } catch (_: InterruptedException) {
        }
    }
}
