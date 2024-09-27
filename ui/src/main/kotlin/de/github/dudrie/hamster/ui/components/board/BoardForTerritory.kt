package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.ui.model.UIViewModel
import kotlin.math.min

@Composable
fun BoardForTerritory(modifier: Modifier = Modifier, viewModel: UIViewModel = viewModel()) {
    val spielState by viewModel.spielZustand.collectAsState()
    val size = spielState.territorium.abmessungen

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val tileSize = calculateTileSize(constraints, size)

        Column(
            Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max)
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
        ) {
            repeat(size.hohe) { row ->
                Row(Modifier.weight(1f)) {
                    repeat(size.breite) { column ->
                        val position = Position(column, row)

                        GameTile(
                            tile = spielState.territorium.getKachelBei(position),
                            hamster = spielState.territorium.getHamsterBei(position),
                            size = tileSize
                        )
                    }
                }
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
