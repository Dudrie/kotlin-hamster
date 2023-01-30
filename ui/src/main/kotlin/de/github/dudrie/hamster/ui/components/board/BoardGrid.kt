package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import de.github.dudrie.hamster.datatypes.HamsterLocation
import de.github.dudrie.hamster.interfaces.AbstractTerritory

/**
 * Renders the game's territory in a grid with square tiles using [BoardTiles][BoardTile].
 *
 * @see BoardTile
 */
@Composable
fun BoardGrid(
    territory: AbstractTerritory,
    borderWidth: Dp,
    tileContent: @Composable RowScope.(location: HamsterLocation, tileModifier: Modifier) -> Unit
) {
    val size = territory.territorySize

    Column(
        modifier = Modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min).background(Color.Black)
            .padding(vertical = borderWidth)
    ) {
        for (row in 0 until size.rowCount) {
            Row(
                modifier = Modifier.weight(1f).padding(start = borderWidth, end = borderWidth)
            ) {
                for (col in 0 until size.columnCount) {
                    val location = HamsterLocation(col, row)

                    tileContent(
                        location,
                        Modifier.weight(1f).aspectRatio(1f).padding(borderWidth)
                            .background(MaterialTheme.colors.surface)
                    )
                }
            }
        }
    }
}
