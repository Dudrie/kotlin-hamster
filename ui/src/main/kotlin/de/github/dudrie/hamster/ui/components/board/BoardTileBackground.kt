package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.theme.GameTheme

@Composable
fun BoardTileBackground(tile: GameTile) {
    when (tile.type) {
        GameTileType.Wall -> {
            val res = ResourceReader(R.images.wall)
            Image(
                bitmap = res.getContentAsImage().asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        GameTileType.Floor -> {
            Box(modifier = Modifier.background(color = GameTheme.colors.floorColor).fillMaxSize()) {}
        }
    }
}
