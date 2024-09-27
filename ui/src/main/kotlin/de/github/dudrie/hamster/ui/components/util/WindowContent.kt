package de.github.dudrie.hamster.ui.components.util

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.components.appbar.AppBar
import de.github.dudrie.hamster.ui.components.board.BoardForTerritory
import de.github.dudrie.hamster.ui.components.console.ConsolePanel
import de.github.dudrie.hamster.ui.model.UIViewModel

@Composable
fun WindowContent(viewModel: UIViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(Modifier.fillMaxWidth()) {
        AppBar()

        Row(Modifier.fillMaxSize()) {
            BoardForTerritory(Modifier.weight(1f).fillMaxHeight().padding(8.dp))

            if (uiState.showConsole) {
                ConsolePanel(Modifier.width(300.dp).fillMaxHeight())
            }
        }
    }
}
