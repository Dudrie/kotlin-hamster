package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.internal.model.territory.GameTileType
import de.github.dudrie.hamster.ui.R

@Composable
fun BoardTileBackground(tile: GameTile) {
    when (tile.type) {
        GameTileType.Wall -> {
            val res = ResourceReader(R.resources.wall)
            Image(
                bitmap = res.getContentAsImage().asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        GameTileType.Floor -> {
            if (tile.isEmptyTile) {
                Text(
                    text = "Grains: ${tile.grainCount}",
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
