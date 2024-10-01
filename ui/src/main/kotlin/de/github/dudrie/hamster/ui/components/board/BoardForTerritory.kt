package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.ui.model.UIViewModel
import kotlin.math.min

@Composable
fun BoardForTerritory(modifier: Modifier = Modifier, viewModel: UIViewModel = viewModel()) {
    val spielState by viewModel.spielZustand.collectAsState()
    val size = spielState.territorium.abmessungen

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val tileSize = calculateTileSize(constraints, size)

        Box(
            Modifier.size(width = size.breite * tileSize, height = size.hohe * tileSize)
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
        ) {
            spielState.territorium.kacheln.forEach { (position, kachel) ->
                GameTile(
                    tile = kachel,
                    hamster = spielState.territorium.getHamsterBei(position),
                    highlightTile = spielState.fehler?.position == position,
                    size = tileSize,
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
private fun calculateTileSize(constrains: Constraints, size: Abmessungen): Dp =
    with(LocalDensity.current) {
        val tileWidth =
            ((constrains.maxWidth - size.breite * BORDER_WIDTH) / size.breite) / this.density
        val tileHeight =
            ((constrains.maxHeight - size.hohe * BORDER_WIDTH) / size.hohe) / this.density

        min(tileWidth, tileHeight).dp
    }

internal const val BORDER_WIDTH = 1
