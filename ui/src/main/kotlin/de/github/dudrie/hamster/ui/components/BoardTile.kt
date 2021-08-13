package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.execptions.TileRelatedException
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun BoardTile(location: Location, modifier: Modifier) {
    val gameTerritory = HamsterGameLocal.current.territory
    val runtimeException = HamsterGameLocal.current.gameCommands.runtimeException
    val tile = gameTerritory.getTileAt(location)
    var border: BorderStroke? = null

    if (runtimeException is TileRelatedException) {
        if (runtimeException.tile.location == location) {
            // TODO: Better border color for erroneous tile.
            border = BorderStroke(8.dp, Color.Blue)
        }
    }

    // TODO: Use better floor color -> Custom game theme?
    Surface(modifier = modifier.background(Color.LightGray), border = border) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            BoardTileBackground(tile)

            BoardTileContent(tile)
        }
    }
}
