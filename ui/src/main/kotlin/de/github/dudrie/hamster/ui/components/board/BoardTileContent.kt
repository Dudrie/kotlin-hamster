package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.ui.R

/**
 * Returns the amount of degrees the hamster's image has to be turned to visually face in the [direction].
 */
fun getDegreesForDirection(direction: Direction): Float = when (direction) {
    Direction.North -> 270f
    Direction.East -> 0f
    Direction.South -> 90f
    Direction.West -> 180f
}

/**
 * Renders the content of the given [tile].
 */
@Composable
fun BoardTileContent(tile: GameTile, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (tile.grainCount > 0) {
            val grain = remember { ResourceReader(R.images.grain).getContentAsImage().asImageBitmap() }

            Box(
                modifier = Modifier.fillMaxSize().padding(4.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = grain,
                        contentDescription = null,
                        modifier = Modifier.graphicsLayer().width(56.dp)
                    )
                    if (tile.grainCount > 1) {
                        Text("${tile.grainCount}")
                    }
                }
            }

        }

        for (content in tile.tileContent) {
            if (content is HamsterTileContent) {
                val degrees = getDegreesForDirection(content.direction)
                val hamster = remember { ResourceReader(R.images.hamster).getContentAsImage().asImageBitmap() }

                Image(
                    bitmap = hamster,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(4.dp).rotate(degrees),
                )
            }
        }
    }
}
