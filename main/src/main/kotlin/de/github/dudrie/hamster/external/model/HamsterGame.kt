package de.github.dudrie.hamster.external.model

import de.github.dudrie.hamster.importer.InitialGameImporter
import de.github.dudrie.hamster.internal.model.game.Command
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.HamsterGameViewModel

class HamsterGame(territoryFile: String? = null) {
    val hamsterGameViewModel: HamsterGameViewModel

    val gameCommands: GameCommandStack = GameCommandStack()

    val territory: Territory

    init {
        val importer = InitialGameImporter(territoryFile)
        territory = Territory.loadFromImporter(importer = importer, hamsterGame = this)
        val hamster = importer.hamster

        hamsterGameViewModel = HamsterGameViewModel(territory = territory.getInternalTerritory(), hamster = hamster)
    }

    fun startGame(startPaused: Boolean) {
        gameCommands.startGame(startPaused)
    }

    fun executeCommand(command: Command) {
        this.gameCommands.execute(command)
    }
}
