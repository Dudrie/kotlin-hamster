package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GameGridLayout() {
    val gameTerritory = GameViewModelLocal.current.hamsterGame.territory
    val size = gameTerritory.size
    val borderWidth = 1.dp

    val minWidth = Integer.min(size.columnCount * 32, 300)
    val maxWidth = Integer.max(size.columnCount * 64, 1000)

    Box(
        modifier = Modifier.padding(8.dp).fillMaxHeight().widthIn(min = minWidth.dp, max = maxWidth.dp),
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
                        Surface(modifier = Modifier.weight(1f).aspectRatio(1f).padding(borderWidth)) {
                            Text("c: $col, r: $row")
                        }
                    }
                }
            }
        }
    }
}
