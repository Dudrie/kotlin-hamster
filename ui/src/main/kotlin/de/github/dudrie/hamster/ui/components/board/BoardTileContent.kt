package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.helpers.rememberReplaceColor
import de.github.dudrie.hamster.ui.theme.GameTheme

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
            val grain = rememberReplaceColor(Color.Black, GameTheme.colors.grainColor) {
                ResourceReader(R.images.grain).getContentAsImage()
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = grain,
                    contentDescription = null,
                    modifier = Modifier.graphicsLayer().aspectRatio(1f).fillMaxSize()
                )
                if (tile.grainCount > 1) {
                    Surface(
                        color = Color.Gray.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(2.dp).size(36.dp).align(Alignment.BottomEnd)
                    ) {
                        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "${tile.grainCount}",
                                style = GameTheme.typography.grainCount,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

        }

        for (content in tile.tileContent) {
            if (content is HamsterTileContent) {
                val degrees = getDegreesForDirection(content.direction)
                val colors = GameTheme.colors
                val hamster = rememberReplaceColor({ResourceReader(R.images.hamster).getContentAsImage()}) {
                    addColorReplacement(colors.hamsterImage.lightPart, colors.defaultHamster.lightPart)
                    addColorReplacement(colors.hamsterImage.darkPart, colors.defaultHamster.darkPart)
                }

                Image(
                    bitmap = hamster,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(4.dp).rotate(degrees),
                )
            }
        }
    }
}
