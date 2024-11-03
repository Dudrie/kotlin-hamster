package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import de.github.dudrie.hamster.core.extensions.getAbmessungen
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.core.model.util.Position
import kotlin.math.min

@Composable
fun BoardForTiles(
    kacheln: Map<Position, Kachel>,
    hamster: List<InternerHamster>,
    highlightedTile: Position?,
    hideHamster: Boolean,
    mehrAlsEinHamster: Boolean,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val totalSize = remember(kacheln) { kacheln.getAbmessungen() }
        val tileSize = calculateTileSize(constraints, totalSize)
        val listener = LocalGameTileClicked.current

        Box(
            Modifier.size(width = totalSize.breite * tileSize, height = totalSize.hohe * tileSize)
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
        ) {
            kacheln.forEach { (position, kachel) ->
                GameTile(
                    tile = kachel,
                    onClick = listener?.let { { it(position) } },
                    hamster = hamster.find { it.position == position },
                    highlightTile = highlightedTile == position,
                    size = tileSize,
                    hideHamster = hideHamster,
                    mehrAlsEinHamster = mehrAlsEinHamster,
                    offset = DpOffset(
                        x = (tileSize * position.x),
                        y = (tileSize * position.y)
                    )
                )
            }
        }
    }
}

@Composable
private fun calculateTileSize(constrains: Constraints, size: Abmessungen): Dp {
    val density = LocalDensity.current
    return remember(constrains, size) {
        with(density) {
            val tileWidth =
                ((constrains.maxWidth - size.breite * BORDER_WIDTH) / size.breite) / this.density
            val tileHeight =
                ((constrains.maxHeight - size.hohe * BORDER_WIDTH) / size.hohe) / this.density

            min(tileWidth, tileHeight).dp
        }
    }
}
