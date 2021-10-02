package de.github.dudrie.hamster.external.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import de.github.dudrie.hamster.importer.InitialGameImporter
import de.github.dudrie.hamster.interfaces.AbstractHamsterGame
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.ui.game.HamsterGameCompose
import kotlinx.coroutines.*

/**
 * Base game class to load and start a hamster game.
 *
 * @param territoryFile The path to a territory file which should get loaded. If omitted a default territory is loaded instead.
 */
class HamsterGame(territoryFile: String? = null) : AbstractHamsterGame() {

    /**
     * [GameCommandStack] associated with this [HamsterGame].
     */
    override val gameCommands: GameCommandStack = GameCommandStack()

    /**
     * [Territory] of this game.
     *
     * Is only initialized if [isInitialized] is `true`.
     */
    override lateinit var territory: Territory
        private set

    /**
     * The game is initialized after the corresponding territory is fully loaded.
     */
    override val isInitialized: MutableState<Boolean> = mutableStateOf(false)

    /**
     * Default [Hamster] used in the game.
     *
     * Is only initialized if [isInitialized] is `true`.
     */
    lateinit var paule: Hamster
        private set

    private val composeGameHandler: HamsterGameCompose = HamsterGameCompose(this)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val importer = InitialGameImporter(hamsterGame = this@HamsterGame, territoryFile = territoryFile)

            territory = importer.territory
            paule = importer.hamster
            isInitialized.value = true
        }
    }

    /**
     * Starts a game.
     *
     * @param startPaused If `true` the game gets fully initialized but will then be moved into the [paused mode][GameMode.Paused] instead of the [running mode][GameMode.Running].
     */
    override fun startGame(startPaused: Boolean) {
        composeGameHandler.startGame()
        gameCommands.startGame(startPaused)
    }

    /**
     * Executes the given [command] to change the state of this game.
     */
    override fun executeCommand(command: Command) {
        this.gameCommands.executeCommand(command)
    }
}
