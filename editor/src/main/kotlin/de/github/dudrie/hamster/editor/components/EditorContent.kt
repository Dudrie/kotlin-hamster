package de.github.dudrie.hamster.editor.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.components.editpanel.EditPanel
import de.github.dudrie.hamster.editor.components.toolbox.ToolBox
import de.github.dudrie.hamster.editor.model.EditorUIState
import de.github.dudrie.hamster.ui.components.board.BoardForTiles
import de.github.dudrie.hamster.ui.components.board.LocalGameTileClicked

@Composable
fun EditorContent(modifier: Modifier = Modifier, state: EditorUIState = viewModel()) {
    Row(modifier) {
        val listener = { position: Position ->
            state.selectedTool.apply(position, state)
        }

        CompositionLocalProvider(LocalGameTileClicked provides listener) {
            BoardForTiles(
                kacheln = state.tiles,
                hamster = state.hamster,
                highlightedTile = state.selectedPosition,
                hideHamster = false,
                mehrAlsEinHamster = state.hamster.size > 1,
                modifier = Modifier.weight(1f).fillMaxHeight().padding(8.dp)
            )
        }

        VerticalDivider(Modifier.padding(horizontal = 8.dp))

        Box(Modifier.width(300.dp)) {
            ToolBox(Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp))

            EditPanel(
                visible = state.selectedPosition != null,
                position = state.selectedPosition,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp)
            )
        }
    }
}
