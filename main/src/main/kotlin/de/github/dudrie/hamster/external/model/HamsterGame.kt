package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.importer.InitialGameImporter
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack

class HamsterGame(territoryFile: String? = null) {

    val gameCommands: GameCommandStack = GameCommandStack()

    val territory: Territory

    val hamster: Hamster

    init {
        val importer = InitialGameImporter(hamsterGame = this, territoryFile = territoryFile)
        territory = importer.territory
        hamster = importer.hamster
        hamsterGameViewModel =
            HamsterGameViewModel(territory = territory.internalTerritory, hamster = hamster.internalHamster)
    }

    fun startGame(startPaused: Boolean) {
        gameCommands.startGame(startPaused)
    }

    fun executeCommand(command: Command) {
        this.gameCommands.executeCommand(command)
    }
}
