package de.github.dudrie.hamster.interfaces

import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode

interface IHamsterGame {
    /**
     * [GameCommandStack] associated with this [IHamsterGame].
     */
    val gameCommands: GameCommandStack

    /**
     * [ITerritory] of this game.
     */
    val territory: ITerritory

    /**
     * Starts a game.
     *
     * @param startPaused If `true` the game gets fully initialized but will then be moved into the [paused mode][GameMode.Paused] instead of the [running mode][GameMode.Running].
     */
    fun startGame(startPaused: Boolean = true)

    /**
     * Executes the given [command] to change the state of this game.
     */
    fun executeCommand(command: Command)

}
