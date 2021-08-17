package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.ui.components.board.BoardGrid
import de.github.dudrie.hamster.ui.components.board.BoardTile
import kotlinx.coroutines.flow.collect

/**
 * Board used in the editor.
 *
 * @param territory Territory which should be shown as the current board.
 * @param modifier Modifiers applied to the underlying [Box].
 */
@Composable
fun BoardForEditor(territory: AbstractEditableTerritory, modifier: Modifier = Modifier) {
    val size = territory.territorySize

    val minWidth = Integer.min(size.columnCount * 32, 300)
    val maxWidth = Integer.max(size.columnCount * 64, 1000)

    Box(
        modifier = modifier.padding(16.dp).widthIn(min = minWidth.dp, max = maxWidth.dp),
        contentAlignment = Alignment.Center
    ) {
        BoardGrid(territory, 1.dp) { location, tileModifier ->
            var editedTile by EditorState.editedTile
            val interactionSource = remember { MutableInteractionSource() }
            val tile = remember(location) { territory.getTileAt(location) }

            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    if (it is PressInteraction.Release) {
                        editedTile = EditedTile(tile)
                    }
                }
            }

            BoardTile(tile = tile, showBorder = editedTile?.location == location, modifier = tileModifier.clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = editedTile?.location != location
            ) {})
        }
    }
}
