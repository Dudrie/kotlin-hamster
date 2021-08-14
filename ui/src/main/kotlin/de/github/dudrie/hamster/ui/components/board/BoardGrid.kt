package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun BoardGrid(modifier: Modifier = Modifier) {
    val gameTerritory = HamsterGameLocal.current.territory
    val size = gameTerritory.territorySize
    val borderWidth = 1.dp

    val minWidth = Integer.min(size.columnCount * 32, 300)
    val maxWidth = Integer.max(size.columnCount * 64, 1000)

    Box(
        modifier = modifier.padding(16.dp).widthIn(min = minWidth.dp, max = maxWidth.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min).background(Color.Black)
        ) {
            for (row in 0 until size.rowCount) {
                Row(
                    modifier = Modifier.weight(1f).padding(start = borderWidth, end = borderWidth)
                ) {
                    for (col in 0 until size.columnCount) {
                        BoardTile(
                            Location(column = col, row = row),
                            Modifier.weight(1f).aspectRatio(1f).padding(borderWidth)
                        )
                    }
                }
            }
        }
    }
}

