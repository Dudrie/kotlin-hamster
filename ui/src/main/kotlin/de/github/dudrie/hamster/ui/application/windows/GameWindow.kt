package de.github.dudrie.hamster.ui.application.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.interfaces.AbstractHamsterGame
import de.github.dudrie.hamster.ui.application.HamsterGameLocal
import de.github.dudrie.hamster.ui.game.MainGameUI

/**
 * Window showing the game's UI.
 *
 * @param hamsterGame Game which holds the data to display in the window.
 */
class GameWindow(private val hamsterGame: AbstractHamsterGame) : ApplicationWindow(ResString.get("window.game.title")) {

    /**
     * Renders the UI for the game.
     */
    @Composable
    override fun content() {
        CompositionLocalProvider(
            HamsterGameLocal provides hamsterGame
        ) {
            MainGameUI()
        }
    }
}
