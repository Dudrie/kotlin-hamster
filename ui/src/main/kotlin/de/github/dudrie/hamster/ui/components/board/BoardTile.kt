package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.internal.model.territory.GameTile

/**
 * Responsible for rendering a single tile of a territory.
 *
 * It shows the [tile] according to its [type][de.github.dudrie.hamster.internal.model.territory.GameTile.type] and its [content][de.github.dudrie.hamster.internal.model.territory.GameTile.tileContent].
 *
 * @see BoardTileBackground
 * @see BoardTileContent
 */
@Composable
fun BoardTile(tile: GameTile, showBorder: Boolean = false, modifier: Modifier = Modifier) {
    var border: BorderStroke? = null

    if (showBorder) {
        border = BorderStroke(4.dp, Color.Blue)
    }

    Surface(modifier = modifier, border = border) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            BoardTileBackground(tile)

            BoardTileContent(tile)
        }
    }
}
