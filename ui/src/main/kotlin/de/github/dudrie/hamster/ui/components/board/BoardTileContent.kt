package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Richtung
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.hamster.HamsterTileContent
import de.github.dudrie.hamster.internal.model.territory.GameTile
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.theme.GameTheme

/**
 * Returns the amount of degrees the hamster's image has to be turned to visually face in the [direction].
 */
fun getDegreesForDirection(direction: Richtung): Float = when (direction) {
    Richtung.Norden -> 270f
    Richtung.Osten -> 0f
    Richtung.Sueden -> 90f
    Richtung.Westen -> 180f
}

/**
 * Renders the content of the given [tile].
 */
@Composable
fun BoardTileContent(tile: GameTile, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (tile.grainCount > 0) {
            val grain = remember {
                ResourceReader(R.images.grain).getContentAsImage().toComposeImageBitmap()
            }
            val infoText: String? = remember(tile.grainCount, tile.hideGrainCount) {
                if (tile.hideGrainCount) {
                    "?"
                } else if (tile.grainCount == 1) {
                    null
                } else {
                    "${tile.grainCount}"
                }
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
                if (infoText != null) {
                    Surface(
                        color = Color.Gray.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(2.dp).size(36.dp).align(Alignment.BottomEnd)
                    ) {
                        Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                            Text(
                                text = infoText,
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
                BoardHamsterTileContent(content)
            }
        }
    }
}

