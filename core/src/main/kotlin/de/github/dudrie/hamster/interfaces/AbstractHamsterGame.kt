package de.github.dudrie.hamster.interfaces

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import de.github.dudrie.hamster.execptions.TileRelatedException
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Helper interface for the actual hamster game used during a game.
 */
abstract class AbstractHamsterGame {
    /**
     * [GameCommandStack] associated with this [AbstractHamsterGame].
     */
    abstract val gameCommands: GameCommandStack

    /**
     * [AbstractTerritory] of this game.
     */
    abstract val territory: AbstractTerritory

    /**
     * State which tracks if the game is initialized.
     */
    abstract val isInitialized: MutableState<Boolean>

    /**
     * State that holds the tile which should be highlighted.
     *
     * If there is no such tile the state holds `null`.
     */
    private val tileToHighlightState: State<GameTile?>
        @Composable
        get() {
            return produceState<GameTile?>(null, gameCommands.gameException) {
                val exception = gameCommands.gameException?.exception
                value = if (exception is TileRelatedException) {
                    exception.tile
                } else {
                    null
                }
            }
        }

    /**
     * The [GameTile] which should get highlighted.
     */
    private val tileToHighlight: GameTile?
        @Composable
        get() = tileToHighlightState.value

    /**
     * Starts a game.
     *
     * @param startPaused If `true` the game gets fully initialized but will then be moved into the [paused mode][GameMode.Paused] instead of the [running mode][GameMode.Running].
     */
    abstract fun startGame(startPaused: Boolean = true)

    /**
     * Stops a running game.
     */
    fun stopGame() = gameCommands.stopGame()

    /**
     * Pauses a running game.
     */
    fun pauseGame() = gameCommands.pauseGame()

    /**
     * Resumes a paused game.
     */
    fun resumeGame() = gameCommands.resumeGame()

    /**
     * Executes the given [command] to change the state of this game.
     */
    abstract fun executeCommand(command: Command)

    /**
     * The territory of the game.
     */
    operator fun component1(): AbstractTerritory = territory

    /**
     * The command stack of the game.
     */
    operator fun component2(): GameCommandStack = gameCommands

    /**
     * The tile which should be highlighted.
     *
     * If none should be highlighted `null` is returned.
     */
    @Composable
    operator fun component3(): GameTile? = tileToHighlight

}
