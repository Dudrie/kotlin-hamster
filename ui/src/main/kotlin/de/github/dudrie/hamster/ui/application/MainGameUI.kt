package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.components.appbar.AppBar
import de.github.dudrie.hamster.ui.components.board.BoardGrid
import de.github.dudrie.hamster.ui.components.console.ConsolePanel

@Composable
fun MainGameUI() {
    Scaffold(
        topBar = { AppBar() }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            BoardGrid(Modifier.weight(1f).fillMaxHeight())

            ConsolePanel(Modifier.fillMaxHeight().width(300.dp))
        }
    }
}
