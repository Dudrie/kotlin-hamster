package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.importer.InitialGameImporter
import de.github.dudrie.hamster.interfaces.IHamsterGame
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.ui.application.HamsterGameCompose

/**
 * Base game class to load and start a hamster game.
 *
 * @param territoryFile The path to a territory file which should get loaded. If omitted a default territory is loaded instead.
 */
class HamsterGame(territoryFile: String? = null) : IHamsterGame {

    /**
     * [GameCommandStack] associated with this [HamsterGame].
     */
    override val gameCommands: GameCommandStack = GameCommandStack()

    /**
     * [Territory] of this game.
     */
    override val territory: Territory

    /**
     * Default [Hamster] used in the game.
     */
    val paule: Hamster

    private val composeGameHandler: HamsterGameCompose = HamsterGameCompose(this)

    init {
        val importer = InitialGameImporter(hamsterGame = this, territoryFile = territoryFile)
        territory = importer.territory
        paule = importer.hamster
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
