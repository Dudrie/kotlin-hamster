package de.github.dudrie.kotlin.hamster.ui.state

import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.HamsterGame

class GameViewModel private constructor() {
    companion object {
        var model: GameViewModel = GameViewModel()
            private set
    }

    val hamsterGame = HamsterGame()

    val gameCommands = GameCommandStack()

}
