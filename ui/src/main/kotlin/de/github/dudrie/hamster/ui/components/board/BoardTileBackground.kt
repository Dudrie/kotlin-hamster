package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.helpers.getResource

/**
 * Renders the background of the tile based upon its [type][GameTileType].
 */
@Composable
fun BoardTileBackground(tile: GameTile) {
    val res = ResourceReader(tile.type.getResource())
    Image(
        bitmap = res.getContentAsImage().asImageBitmap(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}
