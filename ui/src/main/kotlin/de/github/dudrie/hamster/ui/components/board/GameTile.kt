package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel

@Composable
fun GameTile(
    tile: Kachel,
    hamster: InternerHamster?,
    highlightTile: Boolean,
    hideHamster: Boolean,
    mehrAlsEinHamster: Boolean,
    size: Dp,
    onClick: (() -> Unit)? = null,
    offset: DpOffset
) {
    val border = if (highlightTile) {
        BorderStroke(4.dp, MaterialTheme.colorScheme.primary)
    } else {
        BorderStroke(BORDER_WIDTH.dp, MaterialTheme.colorScheme.onBackground)
    }
    val clickModifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier

    Box(
        Modifier.offset { IntOffset(offset.x.roundToPx(), offset.y.roundToPx()) }
            .size(size)
            .border(border)
            .then(clickModifier)
    ) {
        GameTileBackground()

        GameTileContent(
            tile = tile,
            hamster = hamster,
            hideHamster = hideHamster,
            mehrAlsEinHamster = mehrAlsEinHamster
        )
    }
}
