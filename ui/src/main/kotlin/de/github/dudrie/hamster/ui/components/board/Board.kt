package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun Board(modifier: Modifier = Modifier) {
    val gameTerritory = HamsterGameLocal.current.territory
    val size = gameTerritory.territorySize

    val minWidth = Integer.min(size.columnCount * 32, 300)
    val maxWidth = Integer.max(size.columnCount * 64, 1000)

    Box(
        modifier = modifier.padding(16.dp).widthIn(min = minWidth.dp, max = maxWidth.dp),
        contentAlignment = Alignment.Center
    ) {
        BoardGrid()

        BoardExceptionDialog()
    }
}
