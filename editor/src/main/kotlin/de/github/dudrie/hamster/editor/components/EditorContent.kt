package de.github.dudrie.hamster.editor.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.components.toolbox.ToolBox
import de.github.dudrie.hamster.editor.model.EditorUIState
import de.github.dudrie.hamster.ui.components.board.BoardForTiles
import de.github.dudrie.hamster.ui.components.board.LocalGameTileClicked

@Composable
fun EditorContent(modifier: Modifier = Modifier, state: EditorUIState = viewModel()) {
    Row(modifier) {
        val listener = { position: Position ->
            println(position)
        }

        CompositionLocalProvider(LocalGameTileClicked provides listener) {
            BoardForTiles(
                kacheln = state.tiles,
                hamster = listOf(),
                highlightedTile = null,
                hideHamster = false,
                modifier = Modifier.weight(1f).fillMaxHeight().padding(8.dp)
            )
        }

        VerticalDivider(Modifier.padding(horizontal = 8.dp))

        ToolBox(Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp).width(300.dp))
    }
}
