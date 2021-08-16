package de.github.dudrie.hamster.ui.game

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.components.appbar.AppBar
import de.github.dudrie.hamster.ui.components.board.BoardForGame
import de.github.dudrie.hamster.ui.components.console.ConsolePanel

/**
 * The root [Composable] for the main game UI.
 *
 * It is responsible for the main layout of the game.
 *
 * @see AppBar
 * @see BoardForGame
 * @see ConsolePanel
 */
@Composable
fun MainGameUI() {
    Scaffold(
        topBar = { AppBar() }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            BoardForGame(Modifier.weight(1f).fillMaxHeight())

            ConsolePanel(Modifier.fillMaxHeight().width(300.dp))
        }
    }
}
