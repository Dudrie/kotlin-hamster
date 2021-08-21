package de.github.dudrie.hamster.ui.application.windows

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.FrameWindowScope
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.interfaces.AbstractHamsterGame
import de.github.dudrie.hamster.ui.application.HamsterGameLocal
import de.github.dudrie.hamster.ui.game.MainGameUI

/**
 * Window showing the game's UI.
 *
 * @param hamsterGame Game which holds the data to display in the window.
 */
class GameWindow(private val hamsterGame: AbstractHamsterGame) :
    ApplicationWindow(HamsterString.get("window.game.title")) {

    /**
     * Renders the UI for the game.
     */
    @Composable
    override fun FrameWindowScope.content() {
        CompositionLocalProvider(
            HamsterGameLocal provides hamsterGame
        ) {
            MainGameUI()
        }
    }
}
