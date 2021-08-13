package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun BoardTile(location: Location, modifier: Modifier) {
    val gameTerritory = HamsterGameLocal.current.territory
    val tile = gameTerritory.getTileAt(location)

    // TODO: Use better floor color -> Custom game theme?
    Box(modifier = modifier.background(Color.LightGray), contentAlignment = Alignment.Center) {
        BoardTileBackground(tile)

        BoardTileContent(tile)
    }
}
