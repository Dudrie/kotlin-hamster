package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.application.LocalHamsterGame

/**
 * Wrapper for the basic components for the game board.
 *
 * @see BoardGrid
 * @see BoardExceptionDialog
 */
@Composable
fun BoardForGame(modifier: Modifier = Modifier) {
    val (territory, gameCommands, tileToHighlight) = LocalHamsterGame.current
    val size = territory.abmessungen

    val minWidth = Integer.min(size.columnCount * 32, 300)
    val maxWidth = Integer.max(size.columnCount * 64, 1000)

    val borderWidth = 1.dp

    Box(
        modifier = modifier.padding(16.dp).widthIn(min = minWidth.dp, max = maxWidth.dp),
        contentAlignment = Alignment.Center
    ) {
        BoardGrid(territory, borderWidth) { location, tileModifier ->
            BoardTile(
                tile = territory.holeFeldBei(location),
                showBorder = tileToHighlight?.location == location,
                modifier = tileModifier
            )
        }

        BoardExceptionDialog(gameCommands.gameException?.exception)
    }
}
