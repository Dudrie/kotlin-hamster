package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.ui.R

fun getDegreesForDirection(direction: Direction): Float = when (direction) {
    Direction.North -> 270f
    Direction.East -> 0f
    Direction.South -> 90f
    Direction.West -> 180f
}

@Composable
fun BoardTileContent(tile: GameTile, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        for (content in tile.tileContent) {
            if (content is GameHamster) {
                val degrees = getDegreesForDirection(content.direction)
                Image(
                    bitmap = ResourceReader(R.resources.hamster).getContentAsImage().asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(4.dp).rotate(degrees),
                )
            }
        }
    }
}
