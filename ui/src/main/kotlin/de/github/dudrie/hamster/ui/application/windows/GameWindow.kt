package de.github.dudrie.hamster.ui.application.windows

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.FrameWindowScope
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.interfaces.AbstractHamsterGame
import de.github.dudrie.hamster.ui.application.HamsterGameLocal
import de.github.dudrie.hamster.ui.application.UIStateLocal
import de.github.dudrie.hamster.ui.application.state.UIState
import de.github.dudrie.hamster.ui.components.appbar.AppBar
import de.github.dudrie.hamster.ui.game.MainGameUI
import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch

/**
 * Window showing the game's UI.
 *
 * @param hamsterGame Game which holds the data to display in the window.
 * @param initLatch [CountDownLatch] which is used to track the initialization state of the game during start up.
 */
class GameWindow(private val hamsterGame: AbstractHamsterGame, private val initLatch: CountDownLatch) :
    ApplicationWindow(HamsterString.get("window.game.title")) {

    private val uiState = UIState()

    /**
     * Renders the UI for the game.
     */
    @Composable
    override fun FrameWindowScope.content() {
        CompositionLocalProvider(
            HamsterGameLocal provides hamsterGame,
            UIStateLocal provides uiState
        ) {
            val isGameInitialized by hamsterGame.isInitialized

            LaunchedEffect(isGameInitialized) {
                if (isGameInitialized) {
                    // Wait a small amount of time to allow the window to change the "scene".
                    delay(200L)
                    initLatch.countDown()
                }
            }

            Scaffold(
                topBar = { AppBar() }
            ) {
                if (isGameInitialized) {
                    MainGameUI()
                } else {
                    LoadingUI()
                }
            }
        }
    }
}
