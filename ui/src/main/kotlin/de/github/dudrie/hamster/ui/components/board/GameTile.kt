package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel

@Composable
fun GameTile(tile: Kachel, hamster: InternerHamster?, size: Dp) {
    Box(Modifier.size(size).border(BORDER_WIDTH.dp, MaterialTheme.colorScheme.onBackground)) {
        GameTileBackground()

        GameTileContent(tile, hamster)
    }
}
