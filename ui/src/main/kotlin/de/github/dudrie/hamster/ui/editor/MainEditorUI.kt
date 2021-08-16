package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.ui.components.board.BoardGrid


@Composable
fun MainEditorUI(territory: AbstractEditableTerritory) {
    Scaffold(
        topBar = {
            TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
                Text("MENU & TOOLS & SETTINGS", color = MaterialTheme.colors.onPrimary)
            }
        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            BoardForEditor(territory, Modifier.weight(1f).fillMaxHeight())

            Toolbox(Modifier.fillMaxHeight().width(300.dp).background(Color.Magenta))
        }
    }
}

@Composable
fun BoardForEditor(territory: AbstractEditableTerritory, modifier: Modifier = Modifier) {
    val size = territory.territorySize

    val minWidth = Integer.min(size.columnCount * 32, 300)
    val maxWidth = Integer.max(size.columnCount * 64, 1000)

    Box(
        modifier = modifier.padding(16.dp).widthIn(min = minWidth.dp, max = maxWidth.dp),
        contentAlignment = Alignment.Center
    ) {
        BoardGrid(territory, 1.dp) { location, tileModifier ->
            Box(tileModifier) {
                Text("$location")
            }
        }
    }
}

@Composable
fun Toolbox(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text("TOOLBOX")
    }

}
